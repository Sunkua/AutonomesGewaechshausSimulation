package gewaeshaus.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

public class Auftrag {

	private static final Logger log = Logger.getLogger( Auftrag.class.getName() );
	
    private int id;
    private List unterauftraege;

    public Auftrag() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }


    public Unterauftrag holeUnterauftrag() {
        return null;

    }

}
