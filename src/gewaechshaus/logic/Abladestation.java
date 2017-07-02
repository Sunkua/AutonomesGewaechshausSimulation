package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;


/**
 * Implementiert die Funktionalität der Abladestation für Früchte.
 */
public class Abladestation {

	private int fuellstand;
	private AbladestationStatus status = AbladestationStatus.frei; // TODO Was ist Status?
	private HashSet<PflanzenArt> pflanzenarten;
	private ArrayList<PflanzenArt> container;
	private Position gridPosition;
	private AblageTyp ablagetyp;

	/**
	 * Initialisiert eine Abladestation ohne Ablagetyp, Art und leerem Füllstand.
	 * 
	 * @param position Position im Gewächtshaus
	 */
	public Abladestation(Position position) {
		this.gridPosition = position;
		fuellstand = 0;
		container = new ArrayList<>();
		pflanzenarten = new HashSet<>();
		ablagetyp = null;
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen.");
	}

	/**
	 * Aktualisiert den Füllstand der Abladestation.
	 * 
	 * @param fuellstand
	 *            Neuer Füllstand
	 */
	public void updateFuellstand(int fuellstand) {
		this.fuellstand = fuellstand;
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Neuer Fuellstand: "+fuellstand);
	}

	/**
	 * Gibt den Füllstand der Abladestation zurück.
	 * 
	 * @return Aktueller Füllstand
	 */
	public int getFuellstand() {
		return fuellstand;
	}

	/**
	 * Leert die Station.
	 */
	public void leeren() {
		fuellstand = 0;
		pflanzenarten.clear();
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Station geleert");
	}

	public HashSet<PflanzenArt> getPflanzenart() {
		return pflanzenarten;
	}

	public void pflanzenArtHinzufuegen(PflanzenArt pArt) {
		this.pflanzenarten.add(pArt);
	}

	public void pflanzenArtEntfernen(PflanzenArt pArt) {
		try {
			this.pflanzenarten.remove(pArt);
		} catch (Exception e) {
			Logging.log(this.getClass().getName(), Level.WARNING, "Pflanzenart nicht im Set der Abladestation vorhanden");
		}
	}

	public void pflanzeAufAbladestationAbladen(PflanzenArt pArt) {
		container.add(pArt);
	}

	public AbladestationStatus getStatus() {
		return status;
	}

	public void setStatus(AbladestationStatus as) {
		this.status = as;
	}

	public Position getGridPosition() {
		return gridPosition;
	}

	public AblageTyp getAblagetyp() {
		return ablagetyp;
	}

	public void setAblagetyp(AblageTyp ablagetyp) {
		this.ablagetyp = ablagetyp;
		
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Neuer Ablagetyp gesetzt: "+ablagetyp.toString());
	}

}
