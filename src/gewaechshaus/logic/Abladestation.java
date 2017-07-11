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


    /**
     * Git die UUID zurück
     *
     * @return
     */
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

    /**
     * Gibt alle Pflanzenarten für die Abladestation zurück
     * @return
     */
    public HashSet<PflanzenArt> getPflanzenart() {
        return pflanzenarten;
    }

    /**
     * Fügt der Abladestation eine Pflanzenart hinzu, für die Sie verantwortlich ist
     * @param pArt Pflanzenart die hinzugefügt werden soll
     */
    public void pflanzenArtHinzufuegen(PflanzenArt pArt) {
        this.pflanzenarten.add(pArt);
    }

    /**
     * Entfernt eine Pflanzenart aus der Verantwortlichkeit der Abladestation
     * @param pArt Pflanzenart die entfernt werden soll
     */
    public void pflanzenArtEntfernen(PflanzenArt pArt) {
        try {
            this.pflanzenarten.remove(pArt);
        } catch (Exception e) {
            Logging.log(this.getClass().getName(), Level.WARNING, "Pflanzenart nicht im Set der Abladestation vorhanden");
        }
    }

    /**
     * Lädt eine Pflanze auf der Station ab
     * @param pArt die Pflanzenart die abgeladen wird
     */
    public void pflanzeAufAbladestationAbladen(PflanzenArt pArt) {
        container.add(pArt);
        setChanged();
        notifyObservers();
    }

    /**
     * Gibt den Status der Abladestation zurück
     * @return Status der Abladestation
     */
    public AbladestationStatus getStatus() {
        return status;
    }

    /**
     * Setzt den Status einer Abladestation
     * @param as Status, den die Abladestation haben soll
     */
    public void setStatus(AbladestationStatus as) {
        this.status = as;
    }

    /**
     * Setzt die Position der Abladestation
     *
     * @return Position der Abladestation
     */
    public Position getPosition() {
        return gridPosition;
    }

    /**
     * gibt den Typ der Abladestation zurück
     * @return Typ der Abladestation
     */
    public AblageTyp getAblagetyp() {
        return ablagetyp;
    }

    /**
     * Setzt den Typ der Abladestation
     * @param ablagetyp Typ der Abladestation
     */
    public void setAblagetyp(AblageTyp ablagetyp) {
        this.ablagetyp = ablagetyp;
        Logging.log(this.getClass().getSimpleName(), Level.INFO, "Neuer Ablagetyp gesetzt: "+ablagetyp.toString());
    }

}
