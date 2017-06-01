package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Implementiert einen Auftrag zum Laden des Akkus.
 */
public class AkkuLaden extends Unterauftrag {

	private static final Logger log = Logger.getLogger( AkkuLaden.class.getName() );

    private Ladestation ladestation;

    public AkkuLaden(Ladestation ladestation) throws SecurityException, IOException {
        Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
        this.ladestation = ladestation;
    }

    /**
     * Startet die Ausführung des Auftrags. 
     */
    @Override
    public void ausfuehren(Roboter roboter) {

    }
}
