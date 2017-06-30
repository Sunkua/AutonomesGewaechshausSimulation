package gewaechshaus.logic;

import java.util.logging.Level;

public class Position implements Comparable<Position> {

    // private static final Logger log = Logger.getLogger(
    // RelativePosition.class.getName() );

    private int spaltenID;
    private int reihenID;

    private double x;
    private double y;

    public Position() {
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }

    /**
     * Erstellt ein neues Positionsobjekt im virtuellen Koordinatensystem
     *
     * @param spalte Spaltenkoordinate (Virtuelle X-Koordinate, muss >= 0 sein)
     * @param zeile  Zeilenkoordinate (Virtuelle Y-Koordinate, muss >= 0 sein)
     */
    public Position(int spalte, int zeile) {
        if (spalte >= 0 && zeile >= 0) {
            spaltenID = spalte;
            reihenID = zeile;
        } else
            throw new IndexOutOfBoundsException("Spalten und Zeilenkoordinaten müssen >= 0 sein");
    }

    /**
     * Erstellt ein neues Positionsobjekt
     *
     * @param x X-Koordinate im Raum (Muss >= 0 sein) entspricht der virtuellen Spaltenkoordinate
     * @param y Y-Koordinate im Raum (Muss >= 0 sein) entspricht der virtuellen Reihenkoordinate
     */
    public Position(double x, double y) {
        if (x >= 0 && y >= 0) {
            this.x = x;
            this.y = y;
            spaltenID = -1;
            reihenID = -1;
        } else
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
     * Gibt die spaltenID (X-Koordinate) im Gitter zurück
     *
     * @return
     */
    public int getSpaltenID() {
        return spaltenID;
    }

    /**
     * Setzt die spaltenID (X-Koordinate) im Gitter
     *
     * @param spaltenID
     */
    public void setSpaltenID(int spaltenID) {
        this.spaltenID = spaltenID;
    }

    /**
     * Gibt die reihenID (Y-Koordinate) im Gitter zurück
     *
     * @return
     */
    public int getReihenID() {
        return reihenID;
    }

    /**
     * Setzt die reihenID (Y-Koordinate) im Gitter
     *
     * @param reihenID
     */
    public void setReihenID(int reihenID) {
        this.reihenID = reihenID;
    }

    /**
     * Berechnet die Reihenkoordinate (Y-Koordinate) im Gitter auf Basis der realen Koordinate
     *
     * @param maxReihen Reihenanzahl des Gitters (Maximal Y-Koordinate + 1)
     * @param maxHoehe  Höhe des Gewächshauses von oben betrachtet in m
     */
    public void berechneReihenPosition(int maxReihen, double maxHoehe) {
        if (maxHoehe > 0 && maxReihen > 0) {
            double reihenHoehe = maxHoehe / maxReihen;
            this.reihenID = (int) Math.round(this.y / reihenHoehe);
        } else {
            throw new IndexOutOfBoundsException("Höhe und maxReihen muss größer als 0 sein");
        }
    }

    /**
     * Berechnet die Spaltenkoordiante (X-Koordinate) im Gitter auf Basis der realen Koordinate
     *
     * @param maxSpalten Spaltenanzahl im Gitter (Maximale X-Koordinate +1)
     * @param maxBreite  Breite des Gewächshauses von oben betrachtet in m
     */
    public void berechneSpaltenPosition(int maxSpalten, double maxBreite) {
        if (maxBreite > 0 && maxSpalten > 0) {
            double spaltenBreite = maxBreite / maxSpalten;
            this.spaltenID = (int) Math.round(this.x / spaltenBreite);
        } else throw new IndexOutOfBoundsException("MaxSpalten und MaxBreite müssen größer als 0 sein");
    }

    @Override
    public int compareTo(Position o) {
        // TODO Auto-generated method stub
        if (o == this) return 0;
        boolean equals = false;
        equals = spaltenID == o.getSpaltenID();
        if (!equals) return -1;
        equals = reihenID == o.getReihenID();
        if (!equals) return 1;
        return 0;

    }

    /**
     * Gibt die Position in lesbarer Form zurück.
     *
     * @return String Position in der Form "X: <XPOS> Y: <YPOS>"
     */
    public String toString() {
        return "X: " + this.getSpaltenID() + " Y: " + this.getReihenID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;
        if (reihenID == -1 || spaltenID == -1) {
            return false;
        }
        if (reihenID == position.getReihenID() && spaltenID == position.getSpaltenID()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = spaltenID;
        result = 31 * result + reihenID;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
