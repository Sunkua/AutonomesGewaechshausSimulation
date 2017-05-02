package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class AkkuLaden extends Unterauftrag {

	private static final Logger log = Logger.getLogger( AkkuLaden.class.getName() );
	
    public AkkuLaden() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    @Override
    public void ausfuehren() {

    }
}
