package gewaeshaus.logic;

public class Ladestation {

    private boolean frei;
    private Position gridPosition;
    private int Status;

    public Ladestation(Position pos) {
        frei = true;
        gridPosition = pos;
    }

    public String getStatus() {
        return null;

    }


    public boolean verbinden() {
        if (frei) {
            frei = false;
        }
        if (!frei) {
            return true;
        } else {
            return false;
        }

    }

}
