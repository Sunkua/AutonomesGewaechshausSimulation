package gewaechshaus.logic;

import java.util.logging.Level;

/**
 * Implementiert einen Auftrag zum Laden des Akkus.
 */
public class AkkuLaden extends Unterauftrag {

    private Ladestation ladestation;

    public AkkuLaden(Ladestation ladestation) {
        this.ladestation = ladestation;
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    /**
     * Startet die Ausführung des Auftrags. 
     */
    @Override
    public void ausfuehren(Roboter roboter) {

    }
}
