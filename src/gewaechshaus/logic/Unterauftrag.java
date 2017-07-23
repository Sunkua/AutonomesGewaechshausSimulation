package gewaechshaus.logic;

import java.util.Observable;
import java.util.Observer;

public abstract class Unterauftrag extends Observable implements Observer {


    protected Roboter roboter;
    protected UnterauftragsStatus status;
    protected Roboterleitsystem roboterleitsystem;
    protected Position zielPosition;
    int zustand = 0;

    /**
     * Konstruktor der abstrakten Klasse Unterauftrag
     */
    public Unterauftrag() {

    }

    /**
     * gibt den Status des Unterauftrags zurück
     *
     * @return
     */
    public UnterauftragsStatus getStatus() {
        return status;
    }

    /**
     * Führt einen Unterauftrag aus
     * @param roboter Roboter auf dem der Unterauftrag ausgeführt werden soll
     */
    public void ausfuehren(Roboter roboter) {

    }

    /**
     * Gibt den Roboter zurück, der dem Unterauftrag zugewiesen ist
     * @return
     */
    public Roboter getRoboter() {
        return roboter;
    }

    /**
     * Setzt den Roboter des Unterauftrags
     * @param r Roboter für den Unterauftrag
     */
    public void setRoboter(Roboter r) {
        this.roboter = r;
        r.setRoboterStatus(RoboterStatus.eBeschaeftigt);
        r.setUnterauftrag(this);
    }

    /**
     * Bricht den Unterauftrag ab
     */
    public void abbrechen() {
        this.status = UnterauftragsStatus.abgebrochen;
    }


}
