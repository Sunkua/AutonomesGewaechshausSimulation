package gewaechshaus.logic;

import java.io.IOException;
import java.util.Observable;
import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public abstract class Unterauftrag extends Observable {


    protected Roboter roboter;
    protected UnterauftragsStatus status;

    public Unterauftrag() {

    }

    public UnterauftragsStatus getStatus() {
        return status;
    }

    public void ausfuehren(Roboter roboter) {

    }

}
