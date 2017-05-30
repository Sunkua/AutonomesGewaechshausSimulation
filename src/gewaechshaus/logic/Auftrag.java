package gewaechshaus.logic;

import java.io.IOException;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

/**
 * Implementiert die Verwaltung von Aufträgen in Unteraufträgen.
 */
public class Auftrag {

    private static final Logger log = Logger.getLogger(Auftrag.class.getName());

    private int id;
    private List unterauftraege;

    public Auftrag() {

    }

    /**
     * Gibt einen einzelnen Unterauftrag zurück.
     *
     * @return Einzelner Unterauftrag
     */
    public Unterauftrag holeUnterauftrag() {
        return null;

    }

    /**
     * Liefert die Liste von Unteraufträgen zurück.
     *
     * @return Unteraufträge
     */
    public List getUnterauftraege() {
        return unterauftraege;
    }

    /**
     * Ersetzt die komplette Liste von Unteraufträgen.
     *
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
