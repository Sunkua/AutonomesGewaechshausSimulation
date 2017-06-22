package gewaechshaus.logic;

import java.util.logging.Level;

public class Position implements Comparable<Position> {

    // private static final Logger log = Logger.getLogger(
    // RelativePosition.class.getName() );

    private Integer SpaltenID;
    private Integer ReihenID;

    private double x;
    private double y;

    public Position() {
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }

    /**
     * Erstellt ein neues Positionsobjekt im virtuellen Koordinatensystem
     * @param spalte Spaltenkoordinate (Virtuelle X-Koordinate, muss >= 0 sein)
     * @param zeile Zeilenkoordinate (Virtuelle Y-Koordinate, muss >= 0 sein)
     */
    public Position(int spalte, int zeile) {
        if (spalte >= 0 && zeile >= 0) {
            SpaltenID = spalte;
            ReihenID = zeile;
        } else
            throw new IndexOutOfBoundsException("Spalten und Zeilenkoordinaten müssen >= 0 sein");
    }

    /**
     * Erstellt ein neues Positionsobjekt
     * @param x X-Koordinate im Raum (Muss >= 0 sein) entspricht der virtuellen Spaltenkoordinate
     * @param y Y-Koordinate im Raum (Muss >= 0 sein) entspricht der virtuellen Reihenkoordinate
     */
    public Position(double x, double y) {
        if(x >= 0 && y >= 0) {
        this.x = x;
        this.y = y;}
        else
            throw new IndexOutOfBoundsException("Koordinaten müssen >= 0 sein");
    }


    /**
     * Gibt die reale X-Koordinate als Fließkommawert zurück
     *
     * @return
     */
    public double getX() {
        return x;
    }

    /**
     * Setzt die reale X-Koordinate
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }


    /**
     * Gibt die reale Y-Koordinate zurück
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * Setzt die reale Y-Koordinate
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gibt die SpaltenID (X-Koordinate) im Gitter zurück
     *
     * @return
     */
    public int getSpaltenID() {
        return SpaltenID;
    }

    /**
     * Setzt die SpaltenID (X-Koordinate) im Gitter
     *
     * @param spaltenID
     */
    public void setSpaltenID(int spaltenID) {
        SpaltenID = spaltenID;
    }

    /**
     * Gibt die ReihenID (Y-Koordinate) im Gitter zurück
     *
     * @return
     */
    public int getReihenID() {
        return ReihenID;
    }

    /**
     * Setzt die ReihenID (Y-Koordinate) im Gitter
     *
     * @param reihenID
     */
    public void setReihenID(int reihenID) {
        ReihenID = reihenID;
    }

    /**
     * Berechnet die Reihenkoordinate (Y-Koordinate) im Gitter auf Basis der realen Koordinate
     *
     * @param maxReihen Reihenanzahl des Gitters (Maximal Y-Koordinate + 1)
     * @param maxHoehe  Höhe des Gewächshauses von oben betrachtet in m
     */
    public void berechneReihenPosition(int maxReihen, double maxHoehe) {
        double reihenHoehe = maxHoehe / maxReihen;
        this.ReihenID = (int) Math.round(this.y / reihenHoehe);

    }

    /**
     * Berechnet die Spaltenkoordiante (X-Koordinate) im Gitter auf Basis der realen Koordinate
     *
     * @param maxSpalten Spaltenanzahl im Gitter (Maximale X-Koordinate +1)
     * @param maxBreite  Breite des Gewächshauses von oben betrachtet in m
     */
    public void berechneSpaltenPosition(int maxSpalten, double maxBreite) {
        double spaltenBreite = maxBreite / maxSpalten;
        this.SpaltenID = (int) Math.round(this.x / spaltenBreite);
    }


    public boolean equals(Object o) {
        if (!(o instanceof Position))
            return false;
        Position p1 = (Position) o;
        boolean reihenMatch = p1.getReihenID() == this.getReihenID();
        boolean spaltenMatch = p1.getSpaltenID() == this.getSpaltenID();
        return (p1.getReihenID() == this.getReihenID() && p1.getSpaltenID() == this.getSpaltenID());
    }

    @Override
    public int compareTo(Position o) {
        // TODO Auto-generated method stub
        if (o == this) return 0;
        int i = SpaltenID.compareTo(o.getSpaltenID());
        if (i != 0) return i;
        return ReihenID.compareTo(o.getReihenID());
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        //return super.hashCode();
        return SpaltenID.hashCode() + 31 * ReihenID.hashCode();
    }

    /**
     * Gibt die Position in lesbarer Form zurück.
     *
     * @return String Position in der Form "X: <XPOS> Y: <YPOS>"
     */
    public String toString() {
        return "X: " + this.getX() + " Y: " + this.getY();
    }

}
