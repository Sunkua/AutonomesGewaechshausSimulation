package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public class Pflanzenverwaltung {
	
	private static final Logger log = Logger.getLogger( Pflanzenverwaltung.class.getName() );

    public void pflanzeHinzufuegen(Einzelpflanze ep) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    public void pflanzeEntfernen(Position p) {

    }

    public Einzelpflanze[] holePflanzenVonArt(PflanzenArt pa) {
        return null;

    }

    public Einzelpflanze[] holePflanzenVonStatus(PflanzenStatus ps) {
        return null;

    }

    public Einzelpflanze holePflanzeVonPosition(Position p) {
        return null;

    }

}
