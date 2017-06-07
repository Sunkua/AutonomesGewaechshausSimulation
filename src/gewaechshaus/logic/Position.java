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
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    public Position(int spalte, int zeile) {
        SpaltenID = spalte;
        ReihenID = zeile;
    }

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Gibt die reale X-Koordinate als Flie�kommawert zur�ck
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
     * Gibt die reale Y-Koordinate zur�ck
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
     * Gibt die SpaltenID (X-Koordinate) im Gitter zur�ck
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
     * Gibt die ReihenID (Y-Koordinate) im Gitter zur�ck
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

}
