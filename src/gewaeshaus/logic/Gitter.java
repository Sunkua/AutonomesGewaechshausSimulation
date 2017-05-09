package gewaeshaus.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.*;

import javafx.util.Pair;

public class Gitter implements e {

	private static final Logger log = Logger.getLogger(Gitter.class.getName());

	Positionsbelegung[][] gitter;
	double gitterhoehe;
	double gitterbreite;

	/**
	 * 
	 * @param hoehe
	 * @param breite
	 * @param horizontalFieldCount
	 * @param verticalFieldCount
	 * @throws SecurityException
	 * @throws IOException
	 * 
	 * Gitteraufbau:  x123
	 * 				  1
	 * 				  2
	 * 				  3	
	 * 				  y
	 * 
	 */
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

	/**
	 * Berechnet die Gitterkoordinaten der eingegebenen Position auf Basis der realen Koordinaten
	 * @param p Position deren karthesische Koordinaten berechnet werden sollen
	 */
	public void toKarthesisch(RelativePosition p) {
		p.berechneReihenPosition(gitter[0].length, gitterhoehe);
		p.berechneSpaltenPosition(gitter.length, gitterbreite);
	}
	
	/**
	 * @param x X-Koordinate vom Ausgangspunkt
	 * @param y Y-Koordinate vom Ausgangspunkt
	 * @return x,y Koordinate von der Gridkoordinate darüber
	 * Wenn schon drüber, dann wird die Ziel-y-Koordinate auf -1 gesetzt
	 */
	public Pair<Integer, Integer> getPositionOben(int x, int y) {
		int zielKoordX = x;
		int zielKoordY = y--;
		if(zielKoordY < 0) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz oben");
		}
		Pair<Integer, Integer> zielKoordinaten = new Pair<Integer, Integer>(zielKoordX, zielKoordY);
		return zielKoordinaten;
	}
	
	/**
	 * @param x X-Koordinate vom Ausgangspunkt
	 * @param y Y-Koordinate vom Ausgangspunkt
	 * @return x,y Gridkoordinate links vom Ursprungspunkt
	 */
	public Pair<Integer, Integer> getPositionLinks(int x, int y) {
		int zielKoordX = x--;
		int zielKoordY = y;
		if(zielKoordX < 0) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		Pair<Integer, Integer> zielKoordinaten = new Pair<Integer, Integer>(zielKoordX, zielKoordY);
		return zielKoordinaten;
	}
	
	/**
	 * @param x X-Koordinate vom Ausgangspunkt
	 * @param y Y-Koordinate vom Ausgangspunkt
	 * @return x,y Gridkoordinate rechts vom Ursprungspunkt
	 * @return
	 */
	public Pair<Integer, Integer> getPositionRechts(int x, int y) {
		int zielKoordX = x++;
		int zielKoordY = y;
		if(zielKoordY >= gitterbreite) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz rechts");
		}
		Pair<Integer, Integer> zielKoordinaten = new Pair<Integer, Integer>(zielKoordX, zielKoordY);
		return zielKoordinaten;
	}
	
	
	/**
	 * @param x X-Koordinate vom Ausgangspunkt
	 * @param y Y-Koordinate vom Ausgangspunkt
	 * @return x,y Gridkoordinate unter dem Ursprungspunkt
	 */
	public Pair<Integer, Integer> getPositionUnten(int x, int y) {
		int zielKoordX = x;
		int zielKoordY = y++;
		if(zielKoordY >= gitterhoehe) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz unten");
		}
		Pair<Integer, Integer> zielKoordinaten = new Pair<Integer, Integer>(zielKoordX, zielKoordY);
		return zielKoordinaten;
	}
	
	/**
	 * @todo Lee-Algorithmus implementieren
	 * @param von Position XY
	 * @param zu Position XY
	 * @return Liste von Gridpositionen die der Reihe nach abgefahren werden müssen
	 */
	public Stack<RelativePosition> kuerzesterWegNach(RelativePosition von, RelativePosition zu) {
		Stack<RelativePosition> positionsListe = new Stack<RelativePosition>();
		
		
		
		
		return positionsListe;
	}


	public void positionStatus(Position p) {
		
	}
}
