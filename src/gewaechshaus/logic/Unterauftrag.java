package gewaechshaus.logic;

import java.util.Observable;
import java.util.Observer;

public abstract class Unterauftrag extends Observable implements Observer {


    protected Roboter roboter;
    protected UnterauftragsStatus status;
    protected Roboterleitsystem roboterleitsystem;
    int zustand = 0;

    public Unterauftrag() {

    }

    public void setRoboter(Roboter r) {
        this.roboter = r;
        r.setRoboterStatus(RoboterStatus.eBeschaeftigt);
    }

    public UnterauftragsStatus getStatus() {
        return status;
    }

    public void ausfuehren(Roboter roboter) {

    }

    public void abbrechen() {
        this.status = UnterauftragsStatus.abgebrochen;
    }

}
