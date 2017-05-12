package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Implementierung der Akkufunktionalität.
 */
public class Akku {
	
    private static final Logger log = Logger.getLogger( Akku.class.getName() );
    
    private double ladestand;
    private double kritischeGrenze;

    /**
     * Generiert einen neuen Akku
     * @param ladestand Aktueller Ladezustand
     * @param kritGrenze Kritischer, unterer Ladezustand
     * @throws SecurityException
     * @throws IOException
     */
    public Akku(double ladestand, double kritGrenze) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
        this.ladestand = ladestand;
        this.kritischeGrenze = kritGrenze;
    }

    public double getLadestand() {
        return this.ladestand;
    }

    /**
     * Setzt den aktuellen Ladezustand
     * @param ladestand Neuer Ladezustand
     */
    public void setLadestand(double ladestand) {
        this.ladestand = ladestand;
    }

    /**
     * Fragt ab ob der Ladezustand des Akkus kritisch ist.
     * @return Kritischer Zustand
     */
    public boolean istKritisch() {
        return (ladestand < kritischeGrenze);
    }
}
