package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

/**
 * Erstellt einen Auftrag zum Erfassen einer einzelnen Pflanze.
 */
public class Einzelscan extends Unterauftrag {
	
	private static final Logger log = Logger.getLogger( Einzelscan.class.getName() );

    public Einzelscan() throws SecurityException, IOException {
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
