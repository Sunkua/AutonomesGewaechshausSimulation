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
	public void toKarthesisch(Position p) {
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
	public Position getPositionOben(Position pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = (pos.getReihenID());
		zielKoordY--;

		if (zielKoordY < 0) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz oben");
		}
		Position zielKoordinaten = new Position();
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
	public Position getPositionLinks(Position pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = pos.getReihenID();
		zielKoordX--;
		if (zielKoordX < 0) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		Position zielKoordinaten = new Position();
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
	public Position getPositionRechts(Position pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = pos.getReihenID();
		zielKoordX++;
		if (zielKoordX >= gitter.length) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		Position zielKoordinaten = new Position();
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
	public Position getPositionUnten(Position pos) throws SecurityException, IOException {
		int zielKoordX = pos.getSpaltenID();
		int zielKoordY = pos.getReihenID();
		zielKoordY++;
		if (zielKoordY >= gitter[0].length) {
			throw new IndexOutOfBoundsException("Ursprung bereits ganz links");
		}
		Position zielKoordinaten = new Position();
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
	private List<Position> getNachbarn(Position pos) {
		List<Position> nachbarn = new ArrayList<Position>();
		try {
			Position links = getPositionLinks(pos);
			nachbarn.add(links);
		} catch (Exception e) {
		}
		try {
			Position rechts = getPositionRechts(pos);
			nachbarn.add(rechts);
		} catch (Exception e) {
		}
		try {
			Position oben = getPositionOben(pos);
			nachbarn.add(oben);
		} catch (Exception e) {
		}
		try {
			Position unten = getPositionUnten(pos);
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
	public ArrayList<Position> kuerzesterWegNach(Position von, Position zu) {
		ArrayList<Position> wegListe = new ArrayList<Position>();
		Integer[][] pfadArray = new Integer[gitter.length][gitter[0].length];
		
		// Array mit -1 füllen
		for (int i = 0; i < pfadArray.length; i++) {
			for (int j = 0; j < pfadArray[0].length; j++) {
				pfadArray[i][j] = -1;
			}
		}

		// Liste mit zu bearbeitenden Positionen (zuletzt gewertete Felder)
		ArrayList<Position> bearbeitung = new ArrayList<Position>();
		
		// Setze von-Position in value-Array auf leer und auf init-Position
		if (gitter[von.getSpaltenID()][von.getReihenID()] == Positionsbelegung.frei)
			pfadArray[von.getSpaltenID()][von.getReihenID()] = 0;
		Position current = von;

		
		int i = 0;
		bearbeitung.add(current);
		
		// Berechne bis die Zielposition erreicht ist
		while (!current.equals(zu)) {
			// Abstand zum Ursprung
			i++;
			
			// Sollte die Liste leer sein (Kein Pfad gefunden)
			if (bearbeitung.isEmpty()) {
				break;
			}
			current = bearbeitung.get(0);

			// Nachbarn aller Knoten zur Bearbeitung einfügen
			List<Position> nachbarn = getNachbarn(current);
			for (Position aktuellePosition : bearbeitung) {
				nachbarn.addAll(getNachbarn(aktuellePosition));

			}
			bearbeitung.clear();

			for (Position pos : nachbarn) {
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

		// Den Pfad zurück laufen. Immer eine kleineren Wert im Array finden und diesen in die Wegliste einfügen
		current = zu;
		wegListe.add(current);

		while (!current.equals(von)) {
			List<Position> nachbarn = getNachbarn(current);
			for(Position p : nachbarn) {
				if(pfadArray[p.getSpaltenID()][p.getReihenID()] < pfadArray[current.getSpaltenID()][current.getReihenID()])
				{
					current = p;
					wegListe.add(p);
					break;
				}
			}
		}
		return wegListe;
	}

	/**
	 * Trägt eine bestimmte Belegung an der Position ein
	 * @param belegung gewünschte Positionsbelegung
	 * @param p Position an der die Belegung eingetragen werden soll
	 */
	public void setPosition(Positionsbelegung belegung, Position p) {
		this.gitter[p.getSpaltenID()][p.getReihenID()] = belegung;
	}
	
	/**
	 * Liest die Belegung der Position aus
	 * @param p Position von der die Belegung gelesen werden soll
	 * @return Positionsbelegung des Positionsparameters
	 */
	public Positionsbelegung getPositionsbelegung(Position p) {
		return gitter[p.getSpaltenID()][p.getReihenID()];
	}
}
