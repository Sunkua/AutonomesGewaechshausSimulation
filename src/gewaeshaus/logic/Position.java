package gewaeshaus.logic;

import java.io.IOException;
import java.util.logging.*;

public class Position {
	
	private static final Logger log = Logger.getLogger( Position.class.getName() );

    int x;
    int y;

    public Position() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
        // TODO Auto-generated constructor stub
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
