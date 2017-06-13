package gewaechshaus.logic;

import java.util.Observable;
import java.util.logging.Level;

/**
 * Erstellt einen Auftrag zum Abladen der Ernte
 */
public class Abladen extends Unterauftrag {

    public Abladen() {
    	Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen.");
    }

    /**
     * Startet die Ausführung des Auftrags.
     */
    @Override
    public void ausfuehren(Roboter roboter) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
