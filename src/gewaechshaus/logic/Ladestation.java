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

    public void verbinden() throws Exception {
        if (frei) {
            frei = false;
        }
        else {
        	throw new Exception("Bereits ein Roboter verbunden");
        }
    }

    public void trennen() throws Exception {
        if (!frei) {
            frei = true;
        }
        else {
        	throw new Exception("Kein Roboter verbunden");
        }
    }
}
