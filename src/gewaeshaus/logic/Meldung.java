package gewaeshaus.logic;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Meldung {
	
	private static final Logger log = Logger.getLogger( Meldung.class.getName() );

    String Meldung;
    Date Zeitstempel;
    Class Melder;

    public Meldung(String text, Class from) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        Meldung = text;
        Zeitstempel = new Date();
        Melder = from;
    }

}
