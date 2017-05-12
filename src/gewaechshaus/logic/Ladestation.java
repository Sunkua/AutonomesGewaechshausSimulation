package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Ladestation {
	
	private static final Logger log = Logger.getLogger( Ladestation.class.getName() );

    private boolean frei;
    private Position gridPosition;
    private int Status;

    public Ladestation(Position pos) throws SecurityException, IOException {    	
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        frei = true;
        gridPosition = pos;
    }

    public String getStatus() {
        return null;

    }


    public boolean verbinden() {
        if (frei) {
            frei = false;
        }
        return !frei;

    }

}
