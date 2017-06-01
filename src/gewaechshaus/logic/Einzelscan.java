package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Erstellt einen Auftrag zum Erfassen einer einzelnen Pflanze.
 */
public class Einzelscan extends Unterauftrag {

    private static final Logger log = Logger.getLogger(Einzelscan.class.getName());

    private Roboter roboter;
    private Einzelpflanze ep;

    public Einzelscan(Einzelpflanze ep) {
        this.ep = ep;
    }



    /**
     * Startet die Ausführung des Auftrags.
     */
    @Override
    public void ausfuehren(Roboter roboter) {
        if (roboter != null) {

        }

    }
}
