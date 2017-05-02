package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public class Status {
	
	private static final Logger log = Logger.getLogger( Status.class.getName() );

    public Status() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }
}
