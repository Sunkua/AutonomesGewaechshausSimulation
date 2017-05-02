package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public abstract class Unterauftrag {
	
	private static final Logger log = Logger.getLogger( Unterauftrag.class.getName() );

    private Position position;

    public Unterauftrag() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    public void ausfuehren() {

    }

}
