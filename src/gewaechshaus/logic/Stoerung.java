package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Stoerung extends Meldung {
	
	private static final Logger log = Logger.getLogger( Stoerung.class.getName() );
	
    private int fehlercode;

    public Stoerung(String text, Class invoker, int fehlercode) throws SecurityException, IOException {
    	super(text, invoker);
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        this.fehlercode = fehlercode;
    }

    public int getFehlercode() {
        return fehlercode;
    }



}
