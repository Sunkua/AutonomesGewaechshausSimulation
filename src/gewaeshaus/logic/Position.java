package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public class Position {
	
	private static final Logger log = Logger.getLogger( Position.class.getName() );

    float x;
    float y;

    public Position() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
        // TODO Auto-generated constructor stub
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}
