package gewaechshaus.logic;

import java.util.logging.Level;


/**
 * Implementiert die Funktionalität der Abladestation für Früchte.
 */
public class Abladestation {

	private int fuellstand;
	private int status; // TODO Was ist Status?
	private PflanzenArt pflanzenart;
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
		pflanzenart = null;
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
		status = 0;
		pflanzenart = null;
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Station geleert");
	}

	public PflanzenArt getPflanzenart() {
		return pflanzenart;
	}

	public void setPflanzenart(PflanzenArt pflanzenart) {
		this.pflanzenart = pflanzenart;
		
		Logging.log(this.getClass().getSimpleName(), Level.INFO, "Neue Pflanzenart gesetzt: "+pflanzenart.toString());
	}

	public int getStatus() {
		return status;
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
