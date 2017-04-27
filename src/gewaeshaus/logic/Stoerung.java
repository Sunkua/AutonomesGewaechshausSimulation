package gewaeshaus.logic;

public class Stoerung extends Meldung {
    private int fehlercode;

    public Stoerung(int fehlercode) {
        this.fehlercode = fehlercode;
    }

    public int getFehlercode() {
        return fehlercode;
    }



}
