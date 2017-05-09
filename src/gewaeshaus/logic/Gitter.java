package gewaeshaus.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.logging.*;
import java.util.stream.IntStream;

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
	 *             Gitteraufbau: x123 1 2 3 y
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
	 * Berechnet die Gitterkoordinaten der eingegebenen Position auf Basis der
	 * realen Koordinaten
	 * 
	 * @param p
	 *            Position deren karthesische Koordinaten berechnet werden
	 *            sollen
	 */
	public void toKarthesisch(RelativePosition p) {
		p.berechneReihenPosition(gitter[0].length, gitterhoehe);
		p.berechneSpaltenPosition(gitter.length, gitterbreite);
	}

	/**
	 * @param x
	 *            X-Koordinate vom Ausgangspunkt
	 * @param y
	 *            Y-Koordinate vom Ausgangspunkt
	 * @return x,y Koordinate von der Gridkoordinate darüber Wenn schon drüber,
	 *         dann wird die Ziel-y-Koordinate auf -1 gesetzt
	 * @throws IOException
	 * @throws SecurityException
	 */
	public RelativePosition getPositionOben(RelativePosition pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = (pos.getReihenID());
		zielKoordY--;

		if (zielKoordY < 0) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz oben");
		}
		RelativePosition zielKoordinaten = new RelativePosition();
		zielKoordinaten.setReihenID(zielKoordY);
		zielKoordinaten.setSpaltenID(zielKoordX);
		return zielKoordinaten;
	}

	/**
	 * @param x
	 *            X-Koordinate vom Ausgangspunkt
	 * @param y
	 *            Y-Koordinate vom Ausgangspunkt
	 * @return x,y Gridkoordinate links vom Ursprungspunkt
	 * @throws IOException
	 * @throws SecurityException
	 */
	public RelativePosition getPositionLinks(RelativePosition pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = pos.getReihenID();
		zielKoordX--;
		if (zielKoordX < 0) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		RelativePosition zielKoordinaten = new RelativePosition();
		zielKoordinaten.setReihenID(zielKoordY);
		zielKoordinaten.setSpaltenID(zielKoordX);
		return zielKoordinaten;
	}

	/**
	 * @param x
	 *            X-Koordinate vom Ausgangspunkt
	 * @param y
	 *            Y-Koordinate vom Ausgangspunkt
	 * @return x,y Gridkoordinate rechts vom Ursprungspunkt
	 * @return
	 * @throws IOException
	 * @throws SecurityException
	 */
	public RelativePosition getPositionRechts(RelativePosition pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = pos.getReihenID();
		zielKoordX++;
		if (zielKoordX >= gitter.length) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		RelativePosition zielKoordinaten = new RelativePosition();
		zielKoordinaten.setReihenID(zielKoordY);
		zielKoordinaten.setSpaltenID(zielKoordX);
		return zielKoordinaten;
	}

	/**
	 * @param x
	 *            X-Koordinate vom Ausgangspunkt
	 * @param y
	 *            Y-Koordinate vom Ausgangspunkt
	 * @return x,y Gridkoordinate unter dem Ursprungspunkt
	 * @throws IOException
	 * @throws SecurityException
	 */
	public RelativePosition getPositionUnten(RelativePosition pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = pos.getReihenID();
		zielKoordY++;
		if (zielKoordY >= gitter[0].length) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		RelativePosition zielKoordinaten = new RelativePosition();
		zielKoordinaten.setReihenID(zielKoordY);
		zielKoordinaten.setSpaltenID(zielKoordX);
		return zielKoordinaten;
	}

	/**
	 * @todo Try Catch kann eventuell ersetzt werden wenn bei Koordinaten der
	 *       Position auf < 0 oder >= gitterbreite geprüft wird
	 * 
	 * @param pos
	 *            Position von der aus Nachbarn gesucht werden sollen
	 * @return Collection der Nachbarknoten wenn vorhanden
	 */
	private List<RelativePosition> getNachbarn(RelativePosition pos) {
		List<RelativePosition> nachbarn = new ArrayList<RelativePosition>();
		try {
			RelativePosition links = getPositionLinks(pos);
			nachbarn.add(links);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			RelativePosition rechts = getPositionRechts(pos);
			nachbarn.add(rechts);
		} catch (Exception e) {
		}
		try {
			RelativePosition oben = getPositionOben(pos);
			nachbarn.add(oben);
		} catch (Exception e) {
		}
		try {
			RelativePosition unten = getPositionUnten(pos);
			nachbarn.add(unten);
		} catch (Exception e) {
		}
		return nachbarn;
	}

	/**
	 * @todo Lee-Algorithmus implementieren
	 * @param von
	 *            Position XY
	 * @param zu
	 *            Position XY
	 * @return Liste von Gridpositionen die der Reihe nach abgefahren werden
	 *         müssen
	 */
	public ArrayList<RelativePosition> kuerzesterWegNach(RelativePosition von, RelativePosition zu) {
		ArrayList<RelativePosition> positionsListe = new ArrayList<RelativePosition>();
		Integer[][] pfadArray = new Integer[gitter.length][gitter[0].length];
		for (int i = 0; i < pfadArray.length; i++) {
			for (int j = 0; j < pfadArray[0].length; j++) {
				pfadArray[i][j] = -1;
			}
		}

		ArrayList<RelativePosition> bearbeitung = new ArrayList<RelativePosition>();
		// Setze von-Position in value-Array auf leer und initialPosition
		if (gitter[von.getSpaltenID()][von.getReihenID()] == Positionsbelegung.frei)
			pfadArray[von.getSpaltenID()][von.getReihenID()] = 0;
		RelativePosition current = von;

		// Solange zu bearbeitende Positionen vorhanden sind oder Ziel erreicht
		// ist
		int i = 0;
		bearbeitung.add(current);
		//while (!((bearbeitung.isEmpty()) || (current.equals(zu)))) {
		while(!current.equals(zu)) {	
		// Abstand zum Ursprung
			i++;
			if (bearbeitung.isEmpty()) {
				break;
			}
			current = bearbeitung.get(0);
			
			// Nachbarn aller Knoten
			List<RelativePosition> nachbarn = getNachbarn(current);
			for (RelativePosition aktuellePosition : bearbeitung) {
				nachbarn.addAll(getNachbarn(aktuellePosition));
				
			}
			bearbeitung.clear();

			for (RelativePosition pos : nachbarn) {
				// Wenn kein Hindernis, dann füge Nachbarn der
				// Bearbeitungsliste
				// hinzu

				if ((gitter[pos.getSpaltenID()][pos.getReihenID()] == Positionsbelegung.frei)
						&& pfadArray[pos.getSpaltenID()][pos.getReihenID()] == -1) {
					pfadArray[pos.getSpaltenID()][pos.getReihenID()] = i;
					bearbeitung.add(pos);
				}
			}
		}

		for (int z = 0; z < pfadArray.length; z++) {
			for (int j = 0; j < pfadArray[0].length; j++) {
				System.out.printf("%5d ", pfadArray[z][j]);
			}
			System.out.println();
		}

		// Backtrack
		current = zu;
		positionsListe.add(current);
		
		Integer currentValue = pfadArray[zu.getSpaltenID()][zu.getReihenID()];
		while(!current.equals(von)) {
			// Finde kleiner bewertete Position
			
			List<RelativePosition> nachbarn = getNachbarn(current);
			for(RelativePosition p : nachbarn) {
				if (pfadArray[p.getSpaltenID()][p.getReihenID()] == currentValue-1) {
					currentValue = pfadArray[p.getSpaltenID()][p.getReihenID()];
					positionsListe.add(p);
					current = p;
					break;
				}
				
			}
		}
		

		return positionsListe;
	}

	public void positionStatus(Position p) {

	}
}
