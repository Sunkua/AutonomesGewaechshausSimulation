package gewaeshaus.logic;

public class Stoerung extends Meldung {
    private int fehlercode;

    public Stoerung(String text, Class invoker, int fehlercode) {
    	super(text, invoker);
        this.fehlercode = fehlercode;
    }

    public int getFehlercode() {
        return fehlercode;
    }



}
