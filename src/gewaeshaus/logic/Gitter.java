package gewaeshaus.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

public class Gitter {

	private static final Logger log = Logger.getLogger(Gitter.class.getName());

	Positionsbelegung[][] gitter;
	double gitterhoehe;
	double gitterbreite;

	public Gitter(double hoehe, double breite, int horizontalFieldCount, int verticalFieldCount)
			throws SecurityException, IOException {
		Handler handler = new FileHandler(Settings.loggingFilePath);
		log.addHandler(handler);
		gitter = new Positionsbelegung[horizontalFieldCount][verticalFieldCount];
		for (int i = 0; i < horizontalFieldCount; i++) {
			for (int j = 0; j < verticalFieldCount; j++) {
				gitter[i][j] = Positionsbelegung.frei;
			}
		}
		gitterhoehe = hoehe;
		gitterbreite = breite;
	}

	public void toKarthesisch(RelativePosition p) {
		p.berechneReihenPosition(gitter[0].length, gitterhoehe);
		p.berechneSpaltenPosition(gitter.length, gitterbreite);
	}

	
	/**
	 * Lee's Algorithmus zur Wegfindung impementieren
	 * @param von Position XY
	 * @param zu Position XY
	 * @return Liste von Gridpositionen die der Reihe nach abgefahren werden müssen
	 */
	public ArrayList<Position> kuerzesterWegNach(RelativePosition von, RelativePosition zu) {
		ArrayList<RelativePosition> positionsListe = new ArrayList<RelativePosition>();
		
		
		return positionsListe;
	}


	public void positionStatus(Position p) {
		
	}
}
