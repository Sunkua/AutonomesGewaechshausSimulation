package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Erstellt einen Auftrag zum Ernten einer einzelnen Pflanze.
 */
public class Einzelernte extends Unterauftrag {
	
	private static final Logger log = Logger.getLogger( Einzelernte.class.getName() );

    private Roboter roboter;
    private Einzelpflanze ep;

    public Einzelernte(Einzelpflanze ep) {
        try {
            Handler handler = new FileHandler(Settings.loggingFilePath);
            log.addHandler(handler);
        } catch (Exception e) {

        }
        this.ep = ep;
    }

    public void setRoboter(Roboter roboter) {
        this.roboter = roboter;
    }

    /**
     * Startet die Ausführung des Auftrags. 
     */
    @Override
    public void ausfuehren() {

    }
}
