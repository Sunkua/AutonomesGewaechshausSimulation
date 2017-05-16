package gewaechshaus.logic;

public class Position {

	// private static final Logger log = Logger.getLogger(
	// RelativePosition.class.getName() );

	private int SpaltenID;
	private int ReihenID;
	private double x;
	private double y;

	public Position() {
		// Handler handler = new FileHandler( Settings.loggingFilePath );
		// log.addHandler( handler );
		// TODO Auto-generated constructor stub
	}
	public Position(int spalte, int zeile) {
		SpaltenID = spalte;
		ReihenID = zeile;
	}
	

	/**
	 * Gibt die reale X-Koordinate als Flie�kommawert zur�ck
	 * @return 
	 */
	public double getX() {
		return x;
	}

	/**
	 * Setzt die reale X-Koordinate
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Gibt die reale Y-Koordinate zur�ck
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * Setzt die reale Y-Koordinate
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Gibt die SpaltenID (X-Koordinate) im Gitter zur�ck
	 * @return
	 */
	public int getSpaltenID() {
		return SpaltenID;
	}

	/**
	 * Setzt die SpaltenID (X-Koordinate) im Gitter
	 * @param spaltenID
	 */
	public void setSpaltenID(int spaltenID) {
		SpaltenID = spaltenID;
	}

	/**
	 * Gibt die ReihenID (Y-Koordinate) im Gitter zur�ck
	 * @return
	 */
	public int getReihenID() {
		return ReihenID;
	}

	/**
	 * Setzt die ReihenID (Y-Koordinate) im Gitter
	 * @param reihenID
	 */
	public void setReihenID(int reihenID) {
		ReihenID = reihenID;
	}

	/**
	 * Berechnet die Reihenkoordinate (Y-Koordinate) im Gitter auf Basis der realen Koordinate
	 * @param maxReihen Reihenanzahl des Gitters (Maximal Y-Koordinate + 1)
	 * @param maxHoehe H�he des Gew�chshauses von oben betrachtet in m
	 */
	public void berechneReihenPosition(int maxReihen, double maxHoehe) {
		double reihenHoehe = maxHoehe / maxReihen;
		this.ReihenID = (int) Math.round(this.y / reihenHoehe);
	}

	/**
	 * Berechnet die Spaltenkoordiante (X-Koordinate) im Gitter auf Basis der realen Koordinate
	 * @param maxSpalten Spaltenanzahl im Gitter (Maximale X-Koordinate +1)
	 * @param maxBreite Breite des Gew�chshauses von oben betrachtet in m
	 */
	public void berechneSpaltenPosition(int maxSpalten, double maxBreite) {
		double spaltenBreite = maxBreite / maxSpalten;
		this.ReihenID = (int) Math.round(this.y / spaltenBreite);
	}

	public boolean equals(Object object2) {
		Position p1 = (Position) object2;
		return p1.getReihenID() == this.getReihenID() && p1.getSpaltenID() == this.getSpaltenID();
	}

}
