package gewaechshaus.logic;

import java.util.logging.Level;

/**
 * Definiert eine einzelne Pflanze und ihre Parameter
 */
public class Einzelpflanze {
	private double gewicht;
	private double reifestatus; // status in 0..100%
	private PflanzenStatus pflanzenStatus; // Status den die Verwalung kennt. zb durch eingabe oder robot Scan
	private Position position;
	private PflanzenArt art;

    /**
     * Erstellt eine Einzelpflanze ohne Attribute
     */
    public Einzelpflanze() {

        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    /**
     * Erstellt eine parametrisierte Einzelpflanze
     * @param art Pflanzenart der Einzelpflanze
     * @param p Position der Einzelpflanze
     * @param gewicht Gewicht der Einzelpflanze
     * @param pflanzenStatus Status der Einzelpflanze (Ob reif, faul etc.)
     */
    public Einzelpflanze(PflanzenArt art, Position p, double gewicht, PflanzenStatus pflanzenStatus) {
        this.art = art;
        this.position = p;
        this.gewicht = gewicht;
        this.pflanzenStatus = pflanzenStatus;
        switch(pflanzenStatus){
            case eReif:
                reifestatus = 100.0;
                break;
            case eUnreif:
                reifestatus = 10.0;
                break;
            default:
                reifestatus = 0.0;
        }

        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    /**
     * Gibt das Gewicht der Pflanze in kg zurück
     * @return Pflanzengewicht in kg
     */
    public double getGewicht() {
        return gewicht;
    }

    /**
     * Setzt das Gewicht der Pflanze in kg
     * @param gewicht Pflanzengewicht in kg
     */
    public void setGewicht(double gewicht) {
        this.gewicht = gewicht;
    }

    /**
     * Gibt den Reifegrad der Pflanze zurück
     * @return
     */
    public double getReifestatus(){
        return reifestatus;
    }


    /**
     * Gibt den Status der Pflanze zurück
     * @return Pflanzenstatus
     */
    public PflanzenStatus getPflanzenstatus() {
        return pflanzenStatus;
    }

    /**
     * Setzt den Pflanzenstatus
     * @param stat Pflanzenstatus
     */
    public void setPflanzenstatus(PflanzenStatus stat) {
        this.pflanzenStatus = stat;
        switch(pflanzenStatus){
            case eReif:
                reifestatus = 100.0;
                break;
            case eUnreif:
                reifestatus = 10.0;
                break;
            default:
                reifestatus = 0.0;
        }
    }

    /**
     * Setzt die Pflanzenart
     * @param art Pflanzenart
     */
	public void setPflanzenart(PflanzenArt art) {
		this.art = art;
    }

    /**
     * Gibt die Position der Pflanze zurück
     * @return Pflanzenposition
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setzt die Position der Pflanze
     *
     * @param p Pfanzenposition
     */
    public void setPosition(Position p) {
        this.position = p;
    }

    /**
     * Gibt die Art der Pflanze zurück
     * @return
     */
    public PflanzenArt getArt() {
        return art;
    }

    /**
     * Setzt die Pflanzenart
     * @param art Pflanzenart
     */
    public void setArt(PflanzenArt art) {
        this.art = art;
    }
	
	/**
	 * Gibt die Informationen der Pflanze in lesbarer Form zurück
	 * @return String Gibt es in der Form "Art: <> Reifegrad <> Position: <>" zurück
	 */
	public String toString() {
		return "Art: "+this.getArt().toString()+" Reifegrad: "+this.getPflanzenstatus().toString()+" Position: "+this.getPosition().toString();
    }

    /**
     * Lässt die Pflanze wachsen / reifen
     */
	public void Wachse(){
		switch(art){
		case eTomate:
			reifestatus += Konstanten.WachstumTomate;
		case eGurke:
			reifestatus += Konstanten.WachstumGurke;
		}

		reifestatus = Math.max(Math.min(reifestatus, 100), 0);
	}
		
}
