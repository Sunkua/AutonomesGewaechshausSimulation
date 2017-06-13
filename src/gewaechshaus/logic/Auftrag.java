package gewaechshaus.logic;


import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Implementiert die Verwaltung von Aufträgen in Unteraufträgen.
 */
public class Auftrag extends Observable implements Observer {

    private int id;
    private List<Unterauftrag> unterauftraege;
    private Clock clock;
    private AuftragsStatus status;

    public Auftrag(Clock clock) {
        this.clock = clock;
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
        this.status = AuftragsStatus.bereit;
    }

    public AuftragsStatus getStatus() {
        return this.status;
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

    public void naechstenUnterauftragAusfuehren(Roboter r) throws Exception {
        Unterauftrag uAuftrag = popUnterauftrag();
        if (r.getStatus() == RoboterStatus.eBereit) {
            uAuftrag.setRoboter(r);
            uAuftrag.addObserver(this);
            clock.addObserver(uAuftrag);
            this.status = AuftragsStatus.ausfuehrend;
        } else {
            throw new Exception("Roboter ist nicht bereit");
        }

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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Unterauftrag) {
            Unterauftrag uAuftrag = (Unterauftrag) o;
            if (uAuftrag.getStatus() == UnterauftragsStatus.beendet) {
                // Unterauftrag als Observer entfernen, damit Ausführen nicht mehr bei jedem Schritt getriggert wird
                clock.deleteObserver(uAuftrag);
                uAuftrag.deleteObservers();
                // Roboterleitsystem benachrichtigen, damit es nächsten Unterauftrag anstoßen kann
                if (this.unterauftraege.size() == 0) {
                    this.status = AuftragsStatus.beendet;
                }
                setChanged();
                notifyObservers();
            }
        }
    }
}
