package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Abladestation {
	
	private static final Logger log = Logger.getLogger( Abladestation.class.getName() );
	
    private int fuellstand;
    private int Status;
    private Position gridPosition;

    public Abladestation() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    public void updateFuellstand(int fuellstand) {

    }

    public int getFuellstand() {
        return fuellstand;
    }


    public void leeren() {

    }


}
