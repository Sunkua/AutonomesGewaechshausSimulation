package gewaechshaus.logic;

import java.util.Observable;
import java.util.logging.Level;

/**
 * Erstellt einen Auftrag zum Erfassen einer einzelnen Pflanze.
 */
public class Einzelscan extends Unterauftrag {

    public Einzelscan() {
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }

    /**
     * Nicht implementiert, lediglich als Stub
     */
    @Override
    public void ausfuehren(Roboter roboter) {
        if (roboter != null) {

        }
    }

    /**
     * Nicht implementiert, lediglich als Stub
     */
    @Override
    public void update(Observable o, Object arg) {

    }
}
