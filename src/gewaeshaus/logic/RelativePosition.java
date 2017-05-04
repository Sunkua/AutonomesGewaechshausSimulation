package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class RelativePosition extends Position {
	
	private static final Logger log = Logger.getLogger( RelativePosition.class.getName() );

    int SpaltenID;
    int ReihenID;

    public RelativePosition() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
        // TODO Auto-generated constructor stub
    }

    public int getSpaltenID() {
        return SpaltenID;
    }

    public void setSpaltenID(int spaltenID) {
        SpaltenID = spaltenID;
    }

    public int getReihenID() {
        return ReihenID;
    }

    public void setReihenID(int reihenID) {
        ReihenID = reihenID;
    }
    
    public void berechneReihenPosition(int maxReihen, float maxHoehe) {
    	float reihenHoehe = maxHoehe / maxReihen;
    	this.ReihenID = Math.round(this.y / reihenHoehe);
    }
    
    public void berechneSpaltenPosition(int maxSpalten, float maxBreite) {
    	float spaltenBreite = maxBreite / maxSpalten;
    	this.ReihenID = Math.round(this.y / spaltenBreite);
    }
}
