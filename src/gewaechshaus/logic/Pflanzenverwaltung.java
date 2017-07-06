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


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Pflanzenverwaltung extends Observable implements Observer{

    /**
     * Hält die Liste von Pflanzen inkl. deren Positionen
     */
    @XmlElement
    private HashMap<Position, Einzelpflanze> pflanzenListe;


    @XmlElement
    private Position maxGröße = new Position(0, 0);
    private double realeBreite;
    private double realeHoehe;
    private int breite;
    private int hoehe;
    private LinkedBlockingQueue<Runnable> runnableQueueToExecute;
    private LinkedBlockingQueue<Runnable> executorQueue;
    private ExecutorService execService;

    public Pflanzenverwaltung(Position maxGroeße) {
        super();
        this.maxGröße = maxGroeße;
        pflanzenListe = new HashMap<>();
        runnableQueueToExecute = new LinkedBlockingQueue<>();
        executorQueue = new LinkedBlockingQueue<Runnable>();
        execService = new ThreadPoolExecutor(1, 1,
                10, TimeUnit.MILLISECONDS,
                executorQueue);

        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;

        Logging.log(this.getClass().getSimpleName(), Level.INFO, "Breite gesetzt: " + breite);
    }



    public int getHoehe() {
        return hoehe;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;

        Logging.log(this.getClass().getSimpleName(), Level.INFO, "Hoehe gesetzt: " + hoehe);
    }

    private boolean ueberpruefePosition(Position p) {
        if (p.getReihenID() <= maxGröße.getReihenID() &&
                p.getSpaltenID() <= maxGröße.getSpaltenID() &&
                p.getX() <= maxGröße.getX() &&
                p.getY() <= maxGröße.getY()) {
            return true;
        }
        return false;
    }

    public void pflanzeHinzufuegen(Einzelpflanze ep) {
        if (!ueberpruefePosition(ep.getPosition())) {
            throw new IndexOutOfBoundsException("Position außerhalb der Pflanzenverwaltung");
        }
        pflanzenListe.put(ep.getPosition(), ep);
        setChanged();
        notifyObservers();

        Logging.log(this.getClass().getSimpleName(), Level.INFO, "Pflanze " + ep.toString() + " hinzugefügt");
    }


    //
    public void pflanzeEntfernen(Position p) {
        Einzelpflanze pflanze = pflanzenListe.get(p);
        if (pflanze == null) {
            Logging.log(this.getClass().getName(), Level.INFO, "Pflanze entfernen Befehl auf leeres Feld angewandt. Nichts wird getan");
        } else {
            pflanzenListe.remove(p);
            setChanged();
            notifyObservers();

            Logging.log(this.getClass().getSimpleName(), Level.INFO, "Pflanze " + pflanze.toString() + " entfernt.");
        }
    }

    public void setMaxGröße(int zeilen, int spalten) {
        maxGröße = new Position(spalten, zeilen);
    }

    public Position getMaxGröße() {
        return maxGröße;
    }

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

    public Map<Position, Einzelpflanze> getPflanzenMapVonTyp(PflanzenArt pArt) {
        return pflanzenListe.entrySet().stream()
                .filter(map -> map.getValue().getArt().equals(pArt))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Position, Einzelpflanze> getPflanzenMapVonStatus(PflanzenStatus pStatus) {
        return pflanzenListe.entrySet().stream()
                .filter(map -> map.getValue().getPflanzenstatus().equals(pStatus))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Position, Einzelpflanze> getAllePflanzen() {
        return pflanzenListe;
    }


    public Einzelpflanze holePflanzeVonPosition(Position p) throws Exception {
        Einzelpflanze pflanze = pflanzenListe.get(p);
        if (pflanze == null) {
            throw new Exception("Keine Pflanze an der Position vorhanden");
        }
        Logging.log(this.getClass().getSimpleName(), Level.INFO, "Pflanze an Position " + p.toString() + " geladen.");
        return pflanze;
    }

    public void pflanzenverwaltungZustandInDateiSpeichern() {
        try {
            JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(this, new File("zustand"));
        } catch (Exception e) {
            Logging.log(this.getClass().getSimpleName(), Level.SEVERE, e.getMessage());
        }
    }

    public void pflanzenVerwaltungZustandAusDateiLesen() {
        try {
            JAXBContext context = JAXBContext.newInstance(Pflanzenverwaltung.class);
            Unmarshaller m = context.createUnmarshaller();
            Pflanzenverwaltung pflanzenverwaltung = (Pflanzenverwaltung) m.unmarshal(new FileReader("zustand"));
            this.pflanzenListe = pflanzenverwaltung.pflanzenListe;
            this.maxGröße = pflanzenverwaltung.maxGröße;
        } catch (Exception e) {
            Logging.log(this.getClass().getSimpleName(), Level.SEVERE, e.getMessage());
        }

    }


    public void löscheAllePflanzen() {
    	this.pflanzenListe.clear();
    }


    private Runnable wachsenRunnableErstellen() {
        Runnable runnable = () -> {
            for (Map.Entry<Position, Einzelpflanze> entry : pflanzenListe.entrySet()) {
                entry.getValue().Wachse();
            }
            setChanged();
            notifyObservers();
        };
        return runnable;
    }

    private void naechstesRunnableAusQueueAusfuehren() {
        if (runnableQueueToExecute.size() > 0 && executorQueue.isEmpty()) {
            execService.execute(runnableQueueToExecute.poll());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
    	if(o instanceof Clock){
            runnableQueueToExecute.add(wachsenRunnableErstellen());
            naechstesRunnableAusQueueAusfuehren();
        }
    }
}
