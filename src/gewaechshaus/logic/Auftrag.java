package gewaechshaus.logic;


import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * Implementiert die Verwaltung von Aufträgen in Unteraufträgen.
 */
public class Auftrag extends Observable implements Observer {

    private List<Unterauftrag> unterauftraege;
    private Clock clock;
    private AuftragsStatus status;
    private LinkedBlockingQueue<Runnable> runnableQueue;
    private LinkedBlockingQueue<Runnable> executorQueue;

    private ExecutorService execService;


    public Auftrag(Clock clock) {
        runnableQueue = new LinkedBlockingQueue<>();
        executorQueue = new LinkedBlockingQueue<>();

        execService = new ThreadPoolExecutor(1, 1,
                10, TimeUnit.MILLISECONDS,
                executorQueue);


        this.clock = clock;
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
     * Sucht den nächsten freien Roboter und führt mit diesem den nächsten freien Unterauftrag aus
     *
     * @param r Roboter mit dem der Unterauftrag ausgeführt werden soll
     * @throws Exception Wirft eine Exception, wenn kein freier Roboter für die Ausführung gefunden wurde
     */
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


    private void naechstesRunnableAusQueueAusfuehren() {
        if (runnableQueue.size() > 0 && executorQueue.isEmpty()) {
            execService.execute(runnableQueue.poll());
        }
    }

    private Runnable unterauftragsRunnableErstellen(Unterauftrag uAuftrag) {
        Runnable r = () -> {
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
        };
        return r;
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
            runnableQueue.add(unterauftragsRunnableErstellen(uAuftrag));
            naechstesRunnableAusQueueAusfuehren();
        }
        if (o instanceof Clock) {
            naechstesRunnableAusQueueAusfuehren();
        }
    }
}
