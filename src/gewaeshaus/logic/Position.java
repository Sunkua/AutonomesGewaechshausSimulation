package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public class Position {
	
	//private static final Logger log = Logger.getLogger( Position.class.getName() );

    double x;
    double y;

    public Position() throws SecurityException, IOException {
    	//Handler handler = new FileHandler( Settings.loggingFilePath );
		//log.addHandler( handler );
        // TODO Auto-generated constructor stub
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
