package gewaeshaus.logic;

public class Gitter {

    int[][] gitter;
    double gitterhoehe;
    double gitterbreite;

    public Gitter(double hoehe, double breite, int horizontalFieldCount, int verticalFieldCount) {
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
