package gewaechshaus.logic;

import java.util.logging.Level;

public class Ladestation {
	
	private boolean frei;
    private Position gridPosition;
    private int Status;

    public Ladestation(Position pos) {    
        frei = true;
        gridPosition = pos;
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }

    public String getStatus() {
        return null;

    }


    public boolean verbinden() {
        if (frei) {
            frei = false;
        }
        return !frei;

    }

}
