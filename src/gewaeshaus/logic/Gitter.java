package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class Gitter {
	
	private static final Logger log = Logger.getLogger( Gitter.class.getName() );

    Positionsbelegung[][] gitter;
    double gitterhoehe;
    double gitterbreite;

    public Gitter(double hoehe, double breite, int horizontalFieldCount, int verticalFieldCount) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
    	gitter = new Positionsbelegung[horizontalFieldCount][verticalFieldCount];
        gitterhoehe = hoehe;
        gitterbreite = breite;
    }

    public void toKarthesisch(RelativePosition p) {
    	p.berechneReihenPosition(gitter[0].length, gitterhoehe);
    	p.berechneSpaltenPosition(gitter.length, gitterbreite);
    }

    public Position kuerzesterWegNach() {
        return null;
    }

    public void nachGitter(Position p) {

    }

    public void positionStatus(Position p) {

    }
}
