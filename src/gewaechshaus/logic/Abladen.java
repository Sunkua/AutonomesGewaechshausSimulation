package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Erstellt einen Auftrag zum Abladen der Ernte
 */
public class Abladen extends Unterauftrag {

    private static final Logger log = Logger.getLogger(Abladen.class.getName());


    private Roboter roboter;

    public Abladen(Roboter roboter) throws SecurityException, IOException {
        Handler handler = new FileHandler(Settings.loggingFilePath);
        log.addHandler(handler);
        this.roboter = roboter;
    }

    /**
     * Startet die Ausführung des Auftrags.
     */
    @Override
    public void ausfuehren() {

    }
}
