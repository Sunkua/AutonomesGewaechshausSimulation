package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

/**
 * Erstellt einen Auftrag zum Ernten einer einzelnen Pflanze.
 */
public class Einzelernte extends Unterauftrag {
	
	private static final Logger log = Logger.getLogger( Einzelernte.class.getName() );

    public Einzelernte() throws SecurityException, IOException {
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
