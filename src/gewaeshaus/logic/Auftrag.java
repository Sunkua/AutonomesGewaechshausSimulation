package gewaeshaus.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.*;

/**
 * Implementiert die Verwaltung von Aufträgen in Unteraufträgen.
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
     * Gibt einen einzelnen Unterauftrag zurück.	
     * @return Einzelner Unterauftrag
     */
    public Unterauftrag holeUnterauftrag() {
        return null;

    }

    /**
     * Liefert die Liste von Unteraufträgen zurück.
     * @return Unterauftr�ge
     */
	public List getUnterauftraege() {
		return unterauftraege;
	}

	/**
	 * Ersetzt die komplette Liste von Unteraufträgen.
	 * @param unterauftraege Neue Unteraufträge
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
