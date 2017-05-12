package gewaechshaus.logic;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Meldung {
	
	private static final Logger log = Logger.getLogger( Meldung.class.getName() );

    private String meldung;
    private Date zeitstempel;
    private Class melder;

    public Meldung(String text, Class from) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        meldung = text;
        zeitstempel = new Date();
        melder = from;
    }

}
