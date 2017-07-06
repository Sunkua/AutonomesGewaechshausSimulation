package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.UUID;
import java.util.logging.Level;


/**
 * Implementiert die Funktionalität der Abladestation für Früchte.
 */
public class Abladestation extends Observable{

	private AbladestationStatus status = AbladestationStatus.frei; // TODO Was ist Status?
	private HashSet<PflanzenArt> pflanzenarten;
	private ArrayList<PflanzenArt> container;
	private Position gridPosition;
	private AblageTyp ablagetyp;
    private UUID id;

	/**
	 * Initialisiert eine Abladestation ohne Ablagetyp, Art und leerem Füllstand.
	 * 
	 * @param position Position im Gewächtshaus
	 */
	public Abladestation(Position position) {
		this.gridPosition = position;
		container = new ArrayList<>();
		pflanzenarten = new HashSet<>();
		ablagetyp = null;
		Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen.");
        id = UUID.randomUUID();
	}


    public UUID getID() {
        return this.id;
    }


	/**
	 * Gibt den Füllstand der Abladestation zurück.
	 * 
	 * @return Aktueller Füllstand
	 */
	public double getFuellstand() {
		double füllstand = 0;
		for (PflanzenArt p : container){
			switch(p){
			case eTomate:
				füllstand += Konstanten.GewichtTomate;
				break;
			case eGurke: 
				füllstand += Konstanten.GewichtGurke;
				break;
			default:
				break;
			}
		}
		return füllstand;
	}

	/**
	 * Leert die Station.
	 */
	public void leeren() {
		container.clear();
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
		setChanged();
		notifyObservers();
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
