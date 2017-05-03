package gewaeshaus.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

/**
 * Implementiert die Verwaltung von Auftr�gen in Unterauftr�gen.
 */
public class Auftrag {

	private static final Logger log = Logger.getLogger( Auftrag.class.getName() );
	
    private int id;
    private List unterauftraege;

    public Auftrag() throws SecurityException, IOException {
    	Handler handler = new FileHandler( Settings.loggingFilePath );
		log.addHandler( handler );
    }

    /**
     * Gibt einen einzelnen Unterauftrag zur�ck.	
     * @return Einzelner Unterauftrag
     */
    public Unterauftrag holeUnterauftrag() {
        return null;

    }

    /**
     * Liefert die Liste von Unterauftr�gen zur�ck.
     * @return Unterauftr�ge
     */
	public List getUnterauftraege() {
		return unterauftraege;
	}

	/**
	 * Ersetzt die komplette Liste von Unterauftr�gen.
	 * @param unterauftraege Neue Unterauftr�ge
	 */
	public void setUnterauftraege(List unterauftraege) {
		this.unterauftraege = unterauftraege;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

}
