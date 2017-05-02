package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class Abladen extends Unterauftrag {

	private static final Logger log = Logger.getLogger( Abladen.class.getName() );
	
    public Abladen() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    @Override
    public void ausfuehren() {

    }
}
