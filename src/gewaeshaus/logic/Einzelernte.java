package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class Einzelernte extends Unterauftrag {
	
	private static final Logger log = Logger.getLogger( Einzelernte.class.getName() );

    public Einzelernte() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    @Override
    public void ausfuehren() {

    }
}
