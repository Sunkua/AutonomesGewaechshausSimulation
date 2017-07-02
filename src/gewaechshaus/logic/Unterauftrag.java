package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

public abstract class Unterauftrag extends Observable implements Observer {


    protected Roboter roboter;
    protected UnterauftragsStatus status;
    protected Roboterleitsystem roboterleitsystem;
    protected Position zielPosition;
    int zustand = 0;

    public Unterauftrag() {

    }

    public UnterauftragsStatus getStatus() {
        return status;
    }

    public void ausfuehren(Roboter roboter) {

    }

    public Roboter getRoboter() {
        return roboter;
    }

    public void setRoboter(Roboter r) {
        this.roboter = r;
        r.setRoboterStatus(RoboterStatus.eBeschaeftigt);
    }

    public void abbrechen() {
        this.status = UnterauftragsStatus.abgebrochen;
    }


}
