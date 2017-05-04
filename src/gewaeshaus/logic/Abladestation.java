package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Implementiert die Funktionalität der Abladestation für Früchte.
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
     * Aktualisiert den Füllstand der Abladestation.
     * @param fuellstand Neuer Füllstand
     */
    public void updateFuellstand(int fuellstand) {

    }

    /**
     * Gibt den Füllstand der Abladestation zurück.
     * @return Aktueller Füllstand
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
