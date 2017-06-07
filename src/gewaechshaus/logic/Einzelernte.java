package gewaechshaus.logic;

import java.util.logging.Level;

/**
 * Erstellt einen Auftrag zum Ernten einer einzelnen Pflanze.
 */
public class Einzelernte extends Unterauftrag {
	
    private Roboter roboter;
    private Einzelpflanze ep;

    public Einzelernte(Einzelpflanze ep) {
        this.ep = ep;
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    /**
     * Startet die Ausführung des Auftrags. 
     */
    @Override
    public void ausfuehren(Roboter roboter) {
        roboter.fahreZu(ep.getPosition());
        if(roboter.scanne(ep.getPosition()) == PflanzenStatus.eReif) {
           if( roboter.schneide());
        }


    }
}
