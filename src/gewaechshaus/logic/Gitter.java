package gewaechshaus.logic;

import java.util.*;
import java.util.logging.Level;

public class Gitter extends Observable implements Observer {

	private final double gitterhoehe;
	private final double gitterbreite;
	private Positionsbelegung[][] gitter;

	/**
	 * Erstellt ein Gitter-Koordinatensystem mit realen und interpolierten
	 * Gitterkoordinaten
	 * <p>
	 * Die Koordinaten des Gitters sehen beispielsweise so aus: x -> 0 1 2 3 y |
	 * 0 1 2 3
	 *
	 * @param hoehe
	 *            Reale Höhe des Gitters
	 * @param breite
	 *            Reale Breite des Gitters
	 * @param horizontalFieldCount
	 *            Anzahl der Felder auf der X-Achse
	 * @param verticalFieldCount
	 *            Anzahl der Felder auf der Y-Achse
	 */
	public Gitter(double hoehe, double breite, int horizontalFieldCount, int verticalFieldCount) {

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
	 * Gibt die Positionsbelegung an einer ReihenID und SpaltenID zurück
	 *
	 * @param reihenID
	 *            Reihenkoordinate bzgl. des Gitters
	 * @param spaltenID
	 *            Spaltenkoordinate bzgl. des Gitters
	 * @return Gibt die Positionsbelegung an der, durch die ReihenID und
	 *         SpaltenID definierten Position zurück
	 */
	public Positionsbelegung getBelegung(int reihenID, int spaltenID) {
		if (reihenID < 0) {
			throw new IndexOutOfBoundsException("Reihe kleiner Null");
		}
		if (reihenID >= getHoehe()) {
			throw new IndexOutOfBoundsException("Reihe größer als breite");
		}
		if (spaltenID < 0) {
			throw new IndexOutOfBoundsException("Spalte kleiner NUll");
		}
		if (spaltenID >= getBreite()) {
			throw new IndexOutOfBoundsException("Spalte größer Breite");
		}
		return gitter[spaltenID][reihenID];
	}

	/**
	 * Gibt die Position über einer Position zurück
	 *
	 * @param pos
	 *            Ursprungsposition
	 * @return obere Position vom Ursprung
	 */
	public Position getPositionOben(Position pos) {
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
	 * Gibt die Position links von einer Position zurück
	 *
	 * @param pos
	 *            Ursprungsposition
	 * @return linke Position vom Ursprung
	 */
	public Position getPositionLinks(Position pos) {
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
	 * Gibt die Position rechts von einer Position zurück
	 *
	 * @param pos
	 *            Ursprungsposition
	 * @return rechte Position vom Ursprung
	 */
	public Position getPositionRechts(Position pos) {
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
	 * Gibt die Position unter einer Position zurück
	 *
	 * @param pos
	 *            Ursprungsposition
	 * @return untere Position vom Ursprung
	 */
	public Position getPositionUnten(Position pos) {
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
	 * Gibt die Nachbarn einer Position zurück
	 *
	 * @param pos
	 *            Position von der aus Nachbarn gesucht werden sollen
	 * @return Collection der Nachbarknoten wenn vorhanden Position auf < 0 oder
	 *         >= gitterbreite geprüft wird
	 */
	private ArrayList<Position> getNachbarn(Position pos) {
		ArrayList<Position> nachbarn = new ArrayList<Position>();
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
	 * Gibt Nachbarfelder einer Position zurück, die als frei gekennzeichnet
	 * sind
	 *
	 * @param p
	 *            Position deren freie Nachbarn gesucht werden sollen
	 * @return Arraylist mit freien Nachbarn
	 */
	public ArrayList<Position> getFreieNachbarFelder(Position p) {
		ArrayList<Position> nachbarn = getNachbarn(p);
		ArrayList<Position> freieNachbarn = new ArrayList<Position>();
		for (Position pos : nachbarn) {
			if (getPositionsbelegung(pos).equals(Positionsbelegung.frei)) {
				freieNachbarn.add(pos);
			}
		}
		return freieNachbarn;
	}

	/**
	 * Löscht die Roboterpositionen aus dem Grid. Dies wird benutzt zum
	 * aktualisieren der Positionen, wenn der / die Roboter sich um ein Kästchen
	 * bewegt / -en
	 */
	public void roboterPositionenBereinigen() {
		for (int i = 0; i < gitter.length; i++) {
			for (int j = 0; j < gitter[0].length; j++) {
				if (gitter[i][j].equals(Positionsbelegung.roboter)) {
					gitter[i][j] = Positionsbelegung.frei;
				}
			}
		}
	}

	/**
	 * Sucht die nächste freie Position für eine Pflanze Gibt die erste, als
	 * Beet gekennzeichnete Position zurück
	 *
	 * @return freie Position für die Pflanze
	 * @throws Exception
	 *             Exception wird geworfen, falls keine freie Position gefunden
	 *             wurde
	 */
	public Position naechsteFreiePflanzenPositionSuchen() throws Exception {
		for (int i = 0; i < this.getBreite(); i++) {
			for (int j = 0; j < this.getHoehe(); j++) {
				if (this.gitter[i][j].equals(Positionsbelegung.beet)) {
					return new Position(i, j);
				}
			}
		} /*
			 * for (int x = 1; x < this.getBreite() - 1; x++) { for (int y = 1;
			 * y < this.getHoehe() - 1; y++) { if (this.gitter[x][y] ==
			 * Positionsbelegung.frei && (x % Konstanten.beetBreite != 0) && (y
			 * % 4 == 2 || y % 4 == 3)) {
			 * 
			 * return new Position(x, y); } } }
			 */
		throw new Exception("Keine freie Position gefunden");
	}

	/**
	 * Berechnet den kürzesten Pfad zwischen 2 Positionen mittels
	 * Lee-Algorithmus
	 *
	 * @param von
	 *            Position XY
	 * @param zu
	 *            Position XY
	 * @return Liste von Gridpositionen die der Reihe nach abgefahren werden
	 *         müssen
	 */
	public ArrayList<Position> kuerzesterWegNach(Position von, Position zu) throws KeinWegGefundenException {
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

			// Nachbarn aller Knoten zur Bearbeitung einf�gen
			List<Position> nachbarn = getNachbarn(current);
			for (Position aktuellePosition : bearbeitung) {
				nachbarn.addAll(getNachbarn(aktuellePosition));

			}
			bearbeitung.clear();

			for (Position pos : nachbarn) {
				// Wenn kein Hindernis, dann füge Nachbarn der
				// Bearbeitungsliste
				// hinzu

				if (gitter[pos.getSpaltenID()][pos.getReihenID()] == Positionsbelegung.frei
						&& pfadArray[pos.getSpaltenID()][pos.getReihenID()] == -1) {
					pfadArray[pos.getSpaltenID()][pos.getReihenID()] = i;
					bearbeitung.add(pos);
				}
			}
		}

		pfadArray[von.getSpaltenID()][von.getReihenID()] = 0;
		// Den Pfad zurück laufen. Immer eine kleineren Wert im Array finden und
		// diesen in die Wegliste einfügen
		current = zu;
		wegListe.add(current);

		int cnt = pfadArray[0].length * pfadArray.length;
		int counter = 0;
		boolean error = false;
		while (!current.equals(von)) {
			if (counter > cnt) {
				error = true;
				break;
			}
			List<Position> nachbarn = getNachbarn(current);
			Logging.log(this.getClass().getName(), Level.INFO, nachbarn.toString());
			for (Position p : nachbarn) {
				if (pfadArray[p.getSpaltenID()][p.getReihenID()] < pfadArray[current.getSpaltenID()][current
						.getReihenID()] && (pfadArray[p.getSpaltenID()][p.getReihenID()] != -1)) {
					current = p;
					wegListe.add(p);
					break;
				}
				counter++;
			}
		}
		if (!error) {
			for (Position p : wegListe) {
				p.setX(p.getSpaltenID());
				p.setY(p.getReihenID());
			}
		} else {
			throw new KeinWegGefundenException("Kein Weg konnte berechnet werden");
		}
		return wegListe;
	}

	/**
	 * @return Gibt die Höhe des Grids in Kästcheneinheiten zurück
	 */
	public int getHoehe() {
		return gitter[0].length;
	}

	/**
	 * @return Gibt die Breite des Grids in Kästcheneinheiten zurück
	 */
	public int getBreite() {
		return gitter.length;
	}

	/**
	 * Trägt eine bestimmte Belegung an der Position ein
	 *
	 * @param belegung
	 *            gewünschte Positionsbelegung
	 * @param p
	 *            Position an der die Belegung eingetragen werden soll
	 */
	public void setPosition(Positionsbelegung belegung, Position p) {
		this.gitter[p.getSpaltenID()][p.getReihenID()] = belegung;
	}

	/**
	 * Initialisiert die Positionen für Pflanzen als Beetbelegungen
	 */
	public void initialisiereBeet() {
		for (int x = 1; x < this.getBreite() - 1; x++) {
			for (int y = 1; y < this.getHoehe() - 1; y++) {
				if (this.gitter[x][y] == Positionsbelegung.frei && (x % Konstanten.beetBreite != 0)
						&& (y % 4 == 2 || y % 4 == 3)) {
					gitter[x][y] = Positionsbelegung.beet;
				}
			}
		}
	}

	public ArrayList<Position> getWege() {
		ArrayList<Position> positionen = new ArrayList<>();
		for (int x = 0; x < this.getBreite(); x++) {
			for (int y = 0; y < this.getHoehe(); y++) {
				if (getPositionsbelegung(x, y).equals(Positionsbelegung.frei)) {
					positionen.add(new Position(x, y));
				}
			}
		}
		return positionen;
	}

	/**
	 * Gibt eine beliebige freie Position für einen Roboter zurück
	 *
	 * @return Position für einen Roboter
	 */
	public Position getNaechsteFreieRoboterPosition() throws Exception {
		Position ergebnis;
		for (int x = 0; x < this.getBreite(); x++) {
			for (int y = 0; y < this.getHoehe(); y++) {
				if (this.gitter[x][y] == Positionsbelegung.frei) {
					ergebnis = new Position(x, y);
					ergebnis.gitterNachKarthesisch(this.gitterbreite, this.gitterhoehe, getBreite(), getHoehe());
					return new Position(x, y);
				}
			}
		}
		throw new Exception("Keine freie Position gefunden");
	}

	/**
	 * Liest die Belegung der Position aus
	 *
	 * @param p
	 *            Position von der die Belegung gelesen werden soll
	 * @return Positionsbelegung des Positionsparameters
	 */
	public Positionsbelegung getPositionsbelegung(Position p) {
		return gitter[p.getSpaltenID()][p.getReihenID()];
	}

	/**
	 * Gibt die Positionsbelegung einer Koordinate aus
	 *
	 * @param x
	 *            x-Koordinate in Kästcheneinheiten
	 * @param y
	 *            Y-Koordinate in Kästcheneinheiten
	 * @return
	 */
	public Positionsbelegung getPositionsbelegung(int x, int y) {
		return gitter[x][y];
	}

	/**
	 * Aktualisiert das Grid abhängig vom triggernden Subjekt
	 *
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Pflanzenverwaltung) {
			Pflanzenverwaltung p = (Pflanzenverwaltung) o;
			Map<Position, Einzelpflanze> pflanzen = p.getAllePflanzen();
			for (Map.Entry<Position, Einzelpflanze> pflanze : pflanzen.entrySet()) {
				this.setPosition(Positionsbelegung.pflanze, pflanze.getKey());
			}
		} else if (o instanceof Roboterleitsystem) {
			Roboterleitsystem leitsystem = (Roboterleitsystem) o;
			// Ladestationen eintragen
			for (Ladestation ls : leitsystem.getLadestationen()) {
				this.setPosition(Positionsbelegung.ladestation, ls.getPosition());
			}

			// Abladestationen eintragen
			for (Abladestation as : leitsystem.getAbladestationen()) {
				this.setPosition(Positionsbelegung.abladestation, as.getPosition());
			}

			for (Roboter r : leitsystem.getRoboter()) {
				this.setPosition(Positionsbelegung.roboter, r.getPosition());
			}
		}
	}
}
