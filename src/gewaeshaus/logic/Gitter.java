package gewaeshaus.logic;

import java.io.IOException;

import java.util.logging.*;

public class Gitter {
	
	private static final Logger log = Logger.getLogger( Gitter.class.getName() );

    int[][] gitter;
    double gitterhoehe;
    double gitterbreite;

    public Gitter(double hoehe, double breite, int horizontalFieldCount, int verticalFieldCount) throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
		
    	gitter = new int[horizontalFieldCount][verticalFieldCount];
        gitterhoehe = hoehe;
        gitterbreite = breite;
    }

    public void toKarthesisch(Position p) {

    }

    public Position kuerzesterWegNach() {
        return null;

    }

    public void nachGitter(Position p) {

    }

    public void positionStatus(Position p) {

    }
}
