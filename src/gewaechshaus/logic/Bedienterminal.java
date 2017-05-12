package gewaechshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;


public class Bedienterminal {
	
	private static final Logger log = Logger.getLogger( Bedienterminal.class.getName() );

    Meldung meldung;

    public void starteEintelernteVonPosition(Position p) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    public void starteErnteVonArt(PflanzenArt pa) {

    }

    public void starteErnteVonStatus(Status s) {

    }

    public void starteEinzelscanVonPosition(Position p) {

    }

    public void starteScan() {

    }

    public void abbrechen() {

    }

    public void pflanzeHinzufuegen(Einzelpflanze ep) {

    }

    public void pflanzeEntfernen(Position p) {

    }

    private void pflanzenUebersicht() {

    }

    private void zeigeMeldungen() {

    }

    private void auftragErstellen() {

    }
}
