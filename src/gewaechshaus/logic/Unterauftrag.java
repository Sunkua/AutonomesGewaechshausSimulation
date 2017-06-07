package gewaechshaus.logic;

import java.util.logging.Level;

public abstract class Unterauftrag {
	
    private Position position;

    private Roboter roboter;

    public Unterauftrag() {
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    public void ausfuehren(Roboter roboter) {

    }

}
