package gewaechshaus.logic;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Implementiert die Verwaltung von Aufträgen in Unteraufträgen.
 */
public class Auftrag extends Observable implements Observer {

    private List<Unterauftrag> unterauftraege;
    private Uhr uhr;
    private AuftragsStatus status;

    private Roboterleitsystem roboterleitsystem;
    private List<Unterauftrag> aktiveUnterauftraege;

    /**
     * Erstellt einen Auftrag
     *
     * @param uhr               Uhr zum Triggern der Ereignisse
     * @param roboterleitsystem Leitsystem zur Verteilung der Unteraufträge an die Roboter
     */
    public Auftrag(Uhr uhr, Roboterleitsystem roboterleitsystem) {
        aktiveUnterauftraege = new ArrayList<>();
        this.roboterleitsystem = roboterleitsystem;
        this.uhr = uhr;
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
        this.status = AuftragsStatus.bereit;
    }


    /**
     * @return Auftragsstatus
     */
    public AuftragsStatus getStatus() {
        return this.status;
    }

    /**
     * Gibt einen einzelnen Unterauftrag zurück. Entfernt dabei den Unterauftrag aus der Queue
     *
     * @return Einzelner Unterauftrag
     */
    public Unterauftrag popUnterauftrag() {
        return unterauftraege.remove(0);
    }


    /**
     * Gibt einen einzelnen Unterauftrag aus der Queue zurück
     *
     * @return Einzelner Unterauftrag
     */
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


    /**
     * Sucht den nächsten freien Roboter und führt mit diesem den nächsten freien Unterauftrag aus.
     * Wenn der Roboter nicht den Status bereit hat, dann führe dem Status entsprechenden Unterauftrag aus
     *
     * @param r Roboter mit dem der Unterauftrag ausgeführt werden soll
     * @throws Exception Wirft eine Exception, wenn kein freier Roboter für die Ausführung gefunden wurde
     */
    public void naechstenUnterauftragAusfuehren(Roboter r) throws Exception {

        if (aktiveUnterauftraege.size() <= roboterleitsystem.getRoboter().size()) {
            Unterauftrag uAuftrag;
            switch (r.getStatus()) {
                case eBereit:
                    uAuftrag = popUnterauftrag();
                    break;
                case eBehaelterVoll:
                    try {
                        uAuftrag = new Abladen(roboterleitsystem, roboterleitsystem.getFreieAbladestation());
                    } catch (Exception e) {
                        // Wenn keine freie Abladestation gefunden wurde, dann warte
                        r.warte();
                        return;
                    }
                    break;
                case eAkkuKritisch:
                    try {
                        uAuftrag = new AkkuLaden(roboterleitsystem, roboterleitsystem.getFreieLadestation());
                    } catch (Exception e) {
                        // Wenn keine freie Ladestation gefunden wurde, dann warte bis Ladestation frei wird
                        r.warte();
                        return;
                    }
                    break;
                default:
                    uAuftrag = null;
            }
            if (uAuftrag != null) {
                // Wenn Roboter keinen Unterauftrag zugewiesen hat
                if (!pruefeObRoboterAktiv(r)) {
                    uAuftrag.setRoboter(r);
                    uAuftrag.addObserver(this);
                    uhr.addObserver(uAuftrag);
                    this.status = AuftragsStatus.ausfuehrend;
                    aktiveUnterauftraege.add(uAuftrag);
                } else {
                    throw new Exception("Roboter ist nicht bereit oder anderer Fehler");
                }
            }
        } else {
            Logging.log(this.getClass().getName(), Level.SEVERE, Thread.currentThread().getStackTrace().toString());
            throw new Exception("Mehr Unteraufträge aktiv als Roboter im System. Fehler wahrscheinlich auf Multithreading zurückzuführen");

        }
    }

    /**
     * Prüft ob ein Roboter gerade einen Unterauftrag abarbeitet
     *
     * @param r zu prüfender Roboter
     * @return true wenn Roboter beschäftigt ist, ansonsten false
     */
    private boolean pruefeObRoboterAktiv(Roboter r) {
        for (Unterauftrag ua : aktiveUnterauftraege) {
            if (ua.getRoboter().equals(r)) {
                return true;
            }
        }
        return false;
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
    public void setUnterauftraege(List<Unterauftrag> unterauftraege) {
        this.unterauftraege = unterauftraege;
    }



    /**
     * Erstellt ein Runnable auf Basis eines Unterauftrags. Das Runnable prüft ob der Unterauftrag beendet wurde und
     * entfernt falls ja die Observer, damit der Unterauftrag nicht weiter getriggert wird.
     *
     * @param uAuftrag Unterauftrag Unterauftrag der im Runnable behandelt werden soll
     * @return Gibt das erstellte Runnable zurück
     */
    private Runnable unterauftragsRunnableErstellen(Unterauftrag uAuftrag) {
        return () -> {
            if (uAuftrag.getStatus().equals(UnterauftragsStatus.beendet)) {
                // Unterauftrag als Observer entfernen, damit Ausführen nicht mehr bei jedem Schritt getriggert wird
                uhr.deleteObserver(uAuftrag);
                Roboter roboter = uAuftrag.getRoboter();
                roboter.setUnterauftrag(null);
                uAuftrag.deleteObservers();
                // Roboterleitsystem benachrichtigen, damit es nächsten Unterauftrag anstoßen kann
                if (this.unterauftraege.size() == 0) {
                    this.status = AuftragsStatus.beendet;

                }
                aktiveUnterauftraege.remove(uAuftrag);
                setChanged();
                notifyObservers();
            }
        };
    }

    /**
     * Update vom Observer
     * Überprüft den Status der Unterauftrags. Ist der Unterauftrag abgeschlossen wird der Nächste angestoßen.
     * Enthält die Queue keine Unteraufträge mehr wird das Leitsystem benachrichtigt, dass der Auftrag abgeschlossen ist
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Unterauftrag) {
            Unterauftrag uAuftrag = (Unterauftrag) o;

            Runnable runnable = () -> {
                if (uAuftrag.getStatus().equals(UnterauftragsStatus.beendet)) {
                    // Unterauftrag als Observer entfernen, damit Ausführen nicht mehr bei jedem Schritt getriggert wird
                    uhr.deleteObserver(uAuftrag);
                    Roboter roboter = uAuftrag.getRoboter();
                    roboter.setUnterauftrag(null);
                    uAuftrag.deleteObservers();
                    // Roboterleitsystem benachrichtigen, damit es nächsten Unterauftrag anstoßen kann
                    if (unterauftraege.size() == 0) {
                        status = AuftragsStatus.beendet;

                    }
                    aktiveUnterauftraege.remove(uAuftrag);
                    setChanged();
                    notifyObservers();
                }
            };

            runnable.run();
        }
    }
}
