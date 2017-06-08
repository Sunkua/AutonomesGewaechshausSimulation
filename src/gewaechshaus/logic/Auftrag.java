package gewaechshaus.logic;


import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Implementiert die Verwaltung von Aufträgen in Unteraufträgen.
 */
public class Auftrag {

    private int id;
    private List<Unterauftrag> unterauftraege;

    public Auftrag() {
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }

    /**
     * Gibt einen einzelnen Unterauftrag zurück.
     *
     * @return Einzelner Unterauftrag
     */
    public Unterauftrag popUnterauftrag() {
        return unterauftraege.remove(0);
    }

    public Unterauftrag peekUnterauftrag() {
        return unterauftraege.get(0);
    }

    /**
     * Liefert die Anzahl der im Auftrag vorhandenen Unteraufträge zurück
     *
     * @return Anzahl der Unteraufträge
     */
    public int getUnterauftragsAnzahl() {
        return unterauftraege.size();
    }

    public void unterauftragAusfuehren(Roboter r) {

    }

    private List<Unterauftrag> getAusfuehrbareUnterauftraege() {
        return unterauftraege.stream()
                .filter(el -> el.status.equals(UnterauftragsStatus.erstellt))
                .collect(Collectors.toList());
    }

    /**
     * Liefert die Liste von Unteraufträgen zurück.
     *
     * @return Unteraufträge
     */
    public List<Unterauftrag> getUnterauftraege() {
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
