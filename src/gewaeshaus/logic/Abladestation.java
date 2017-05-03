package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Implementiert die Funktionalit�t der Abladestation f�r Fr�chte.
 */
public class Abladestation {
	
	private static final Logger log = Logger.getLogger( Abladestation.class.getName() );
	
    private int fuellstand;
    private int status;
    private Position gridPosition;

    public Abladestation() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    /**
     * Aktualisiert den F�llstand der Abladestation.
     * @param fuellstand Neuer F�llstand
     */
    public void updateFuellstand(int fuellstand) {

    }

    /**
     * Gibt den F�llstand der Abladestation zur�ck.
     * @return Aktueller F�llstand
     */
    public int getFuellstand() {
        return fuellstand;
    }

    /**
     * Leert die Station.
     */
    public void leeren() {

    }


}
