package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class Einzelscan extends Unterauftrag {
	
	private static final Logger log = Logger.getLogger( Einzelscan.class.getName() );

    public Einzelscan() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    @Override
    public void ausfuehren() {

    }
}
