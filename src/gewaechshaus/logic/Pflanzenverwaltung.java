package gewaechshaus.logic;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

@XmlRootElement(namespace = "gewaechshaus.logic")
public class Pflanzenverwaltung extends Observable implements Observer {

	/**
	 * Hält die Liste von Pflanzen inkl. deren Positionen
	 */
	@XmlElement
	private HashMap<Position, Einzelpflanze> pflanzenListe;

	@XmlElement
	private Position maxGroesse = new Position(0, 0);
	private Gitter gitter;
	private LinkedBlockingQueue<Runnable> runnableQueueZumAusfuehren;
	private LinkedBlockingQueue<Runnable> executorQueue;
	private ExecutorService execService;

	/**
	 * Konstruktor für die Pflanzenverwaltung
	 *
	 * @param gitter
	 *            gitter für die Maße des Gewächshauses und um die freien
	 *            Positionen zu ermitteln
	 */
	public Pflanzenverwaltung(Gitter gitter) {
		super();
		this.gitter = gitter;
		gitter.initialisiereBeet();
		pflanzenListe = new HashMap<>();
		runnableQueueZumAusfuehren = new LinkedBlockingQueue<>();
		executorQueue = new LinkedBlockingQueue<Runnable>();
		execService = new ThreadPoolExecutor(1, 1, 10, TimeUnit.MILLISECONDS, executorQueue);
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
	}

	/**
	 * Fügt eine Pflanze in das Gewächshaus und die Pflanzenverwaltung ein
	 *
	 * @param pflanzenArt
	 *            Pflanzenart die hinzugefügt werden soll
	 * @throws Exception
	 *             Wirft eine Exception, wenn keine freie Position für die
	 *             Pflanze gefunden wurde
	 */
	public void pflanzeHinzufuegen(PflanzenArt pflanzenArt) throws Exception {

		Position p = gitter.naechsteFreiePflanzenPositionSuchen();
		Einzelpflanze ep = new Einzelpflanze(pflanzenArt, p, Konstanten.GewichtGurke, PflanzenStatus.eUnreif);
		pflanzenListe.put(ep.getPosition(), ep);
		setChanged();
		notifyObservers();
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Pflanze " + ep.toString() + " hinzugefügt");

	}

	/**
	 * Einzelpflanze, die entfernt werden soll
	 *
	 * @param einzelpflanze
	 *            Einzelpflanze, die entfernt werden soll
	 */
	public void pflanzeEntfernen(Einzelpflanze einzelpflanze) {
		if (pflanzenListe.containsKey(einzelpflanze)) {
			pflanzenListe.remove(einzelpflanze);
			setChanged();
			notifyObservers();
			Logging.log(this.getClass().getSimpleName(), Level.INFO,
					"Pflanze " + einzelpflanze.toString() + " entfernt.");
		} else {
			Logging.log(this.getClass().getName(), Level.WARNING, "Keine Pflanze an der Position gefunden. Fehler");
			throw new NoSuchElementException("Keine Pflanze an der Position gefunden");
		}
	}

	/**
	 * Gibt alle Pflanzen einer Art zurück
	 *
	 * @param pa
	 *            Pflanzenart der Pflanzen in der Liste
	 * @return ArrayList mit Einzelpflanzen die der Art pa angehören
	 */
	public ArrayList<Einzelpflanze> holePflanzenVonArt(PflanzenArt pa) {
		ArrayList<Einzelpflanze> einzelpflanzen = new ArrayList<Einzelpflanze>();

		for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
			Einzelpflanze pflanze = entry.getValue();
			if (pflanze.getArt() == pa) {
				einzelpflanzen.add(pflanze);
			}
		}

		return einzelpflanzen;
	}

	/**
	 * Gibt alle Pflanzen mit einem bestimmten Status zurück
	 *
	 * @param ps
	 *            PflanzenStatus der Pflanzen in der Liste
	 * @return ArrayList mit Einzelpflanzen die den Status ps haben
	 */
	public ArrayList<Einzelpflanze> holePflanzenVonStatus(PflanzenStatus ps) {
		ArrayList<Einzelpflanze> einzelpflanzen = new ArrayList<Einzelpflanze>();

		for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
			Einzelpflanze pflanze = entry.getValue();
			if (pflanze.getPflanzenstatus() == ps) {
				einzelpflanzen.add(pflanze);
			}
		}
		return einzelpflanzen;
	}

	/**
	 * Gibt alle Pflanzen von einem bestimmten Typ zurück
	 *
	 * @param pArt
	 *            Pflanzenart der Pflanzen in der Map
	 * @return Map mit Einzelpflanzen die die selbe Pflanzenart pArt haben
	 */
	public Map<Position, Einzelpflanze> getPflanzenMapVonTyp(PflanzenArt pArt) {
		return pflanzenListe.entrySet().stream().filter(map -> map.getValue().getArt().equals(pArt))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * Gibt eine Map mit allen Pflanzen mit einem Status zurück
	 *
	 * @param pStatus
	 *            Status der Pflanzen in der Map
	 * @return
	 */
	public Map<Position, Einzelpflanze> getPflanzenMapVonStatus(PflanzenStatus pStatus) {
		return pflanzenListe.entrySet().stream().filter(map -> map.getValue().getPflanzenstatus().equals(pStatus))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * Gibt eine Map mit allen Pflanzen zurück
	 *
	 * @return Map mit allen Pflanzen in der Pflanzenverwaltung
	 */
	public Map<Position, Einzelpflanze> getAllePflanzen() {
		return pflanzenListe;
	}

	/**
	 * Gibt eine Pflanze von einer bestimmten Position zurück
	 *
	 * @param p
	 *            Position an der nach der Pflanze gesucht werden soll
	 * @return Einzelpflanze der Position p
	 * @throws Exception
	 *             Wirft eine Exception, wenn keine Pflanze gefunden wurde
	 */
	public Einzelpflanze holePflanzeVonPosition(Position p) throws Exception {
		Einzelpflanze pflanze = pflanzenListe.get(p);
		if (pflanze == null) {
			throw new Exception("Keine Pflanze an der Position vorhanden");
		}
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Pflanze an Position " + p.toString() + " geladen.");
		return pflanze;
	}

	/**
	 * Speichert den Zustand der Pflanzenverwaltung in einer Datei
	 */
	public void pflanzenverwaltungZustandInDateiSpeichern() {
		try {
			JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(this, new File("Pflanzenverwaltung_Zustand.xml"));
		} catch (Exception e) {
			Logging.log(this.getClass().getSimpleName(), Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * Liest den Zustand der Pflanzenverwaltung aus einer Datei und spielt
	 * diesen ein
	 */
	public void pflanzenVerwaltungZustandAusDateiLesen() {
		try {
			JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
			Unmarshaller m = context.createUnmarshaller();
			Pflanzenverwaltung pflanzenverwaltung = (Pflanzenverwaltung) m
					.unmarshal(new FileReader("Pflanzenverwaltung_Zustand.xml"));
			this.pflanzenListe = pflanzenverwaltung.pflanzenListe;
			this.maxGroesse = pflanzenverwaltung.maxGroesse;
		} catch (Exception e) {
			Logging.log(this.getClass().getSimpleName(), Level.SEVERE, e.getMessage());
		}

	}

	/**
	 * Löscht alle Pflanzen in der Pflanzenverwaltung
	 */
	public void löscheAllePflanzen() {
		this.pflanzenListe.clear();
	}

	/**
	 * Erstellt das Runnable für das Handling des Pflanzenwachstums
	 *
	 * @return Runnable, das alle Pflanzen wachsen lässt und den Canvas notified
	 */
	private Runnable wachsenRunnableErstellen() {
		return () -> {
			for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
				entry.getValue().wachse();
			}
			setChanged();
			notifyObservers();
		};
	}

	/**
	 * Führt das nächste Runnable aus der Queue aus
	 */
	private void naechstesRunnableAusQueueAusfuehren() {
		if (runnableQueueZumAusfuehren.size() > 0 && executorQueue.isEmpty()) {
			execService.execute(runnableQueueZumAusfuehren.poll());
		}
	}

	/**
	 * Bei Clocktick wird ein Runnable erstellt, in die Queue eingefügt und das
	 * nächste getriggert
	 *
	 * @param o Uhr
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Uhr) {
			runnableQueueZumAusfuehren.add(wachsenRunnableErstellen());
			naechstesRunnableAusQueueAusfuehren();
		}
	}
}
