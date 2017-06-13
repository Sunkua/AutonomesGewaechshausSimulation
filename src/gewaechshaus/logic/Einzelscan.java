package gewaechshaus.logic;

import java.util.Observable;
import java.util.logging.Level;

/**
 * Erstellt einen Auftrag zum Erfassen einer einzelnen Pflanze.
 */
public class Einzelscan extends Unterauftrag {

    private Roboter roboter;
    private Einzelpflanze ep;

    public Einzelscan(Einzelpflanze ep) {
        this.ep = ep;
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }



    /**
     * Startet die Ausführung des Auftrags.
     */
    @Override
    public void ausfuehren(Roboter roboter) {
        if (roboter != null) {

        }

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
