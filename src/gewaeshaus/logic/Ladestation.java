package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

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
        if (!frei) {
            return true;
        } else {
            return false;
        }

    }

}
