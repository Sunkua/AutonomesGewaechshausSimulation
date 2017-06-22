package gewaechshaus.logic;

import java.util.Observable;
import java.util.Observer;

public abstract class Unterauftrag extends Observable implements Observer {


    protected Roboter roboter;
    protected UnterauftragsStatus status;

    public Unterauftrag() {

    }

    public void setRoboter(Roboter r) {
        this.roboter = r;
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
