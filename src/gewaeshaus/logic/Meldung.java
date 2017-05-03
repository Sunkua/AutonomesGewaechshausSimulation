package gewaeshaus.logic;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class Meldung {
	
	private static final Logger log = Logger.getLogger( Meldung.class.getName() );

    String meldung;
    Date zeitstempel;
    Class melder;

    public Meldung(String text, Class from) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        meldung = text;
        zeitstempel = new Date();
        melder = from;
    }

}
