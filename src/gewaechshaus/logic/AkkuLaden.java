package gewaechshaus.logic;

import java.io.IOException;

import java.util.logging.*;

/**
 * Implementiert einen Auftrag zum Laden des Akkus.
 */
public class AkkuLaden extends Unterauftrag {

	private static final Logger log = Logger.getLogger( AkkuLaden.class.getName() );
	
    public AkkuLaden() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    /**
     * Startet die Ausführung des Auftrags. 
     */
    @Override
    public void ausfuehren() {

    }
}
