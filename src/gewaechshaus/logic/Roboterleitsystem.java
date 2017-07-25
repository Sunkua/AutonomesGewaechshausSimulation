package gewaechshaus.logic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Roboterleitsystem extends Observable implements Observer {

    private Queue<Auftrag> auftragsQueue;
    private HashMap<Position, Abladestation> abladestationen;
    private HashMap<Position, Ladestation> ladestationen;
    private List<Roboter> roboterList;
    private Gitter gitter;
    private LinkedBlockingQueue<Runnable> runnableQueueToExecute;
    private LinkedBlockingQueue<Runnable> executorQueue;
    private ExecutorService execService;
    private Uhr uhr;

    /**
     * Konstruktor für ein Roboterleitsystem
     *
     * @param gitter Gitter für die Wegberechnung
     * @param uhr    Uhr zum triggern der Events
     */
    public Roboterleitsystem(Gitter gitter, Uhr uhr) {
        this.uhr = uhr;
        auftragsQueue = new LinkedList<Auftrag>();
        abladestationen = new HashMap<>();
        ladestationen = new HashMap<>();
        roboterList = new ArrayList<Roboter>();
        this.gitter = gitter;
        runnableQueueToExecute = new LinkedBlockingQueue<>();
        executorQueue = new LinkedBlockingQueue<Runnable>();
        execService = new ThreadPoolExecutor(1, 1,
                10, TimeUnit.MILLISECONDS,
                executorQueue);

        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }

    /**
     * Fügt einen Roboter in das Leitsystem ein
     * @param pflanzenverwaltung Die Pflanzenverwaltung für den Roboter
     */
    public void roboterHinzufuegen(Pflanzenverwaltung pflanzenverwaltung) {
        try {
            Roboter roboter = new Roboter(this);
            roboter.setPosition(gitter.getNaechsteFreieRoboterPosition());
            roboter.setRoboterStatus(RoboterStatus.eBereit);
            roboter.addObserver(this);
            roboterList.add(roboter);
            setChanged();
            notifyObservers();
        } catch (Exception e) {
            Logging.log(this.getClass().getName(), Level.WARNING, "Keine freie Position gefunden. Roboter konnte nicht hinzugefügt werden");
        }
    }


    /**
     * Gibt die freien Nachbarfelder einer Position zurück
     * @param p Position deren Nachbarn gesucht werden
     * @return Liste mit freien Nachbarn
     */
    public List<Position> getFreieNachbarFelderVon(Position p) {
        List<Position> posListe = gitter.getFreieNachbarFelder(p);
        return posListe;
    }

    /**
     * Gibt die Positionen der Roboter zurück
     * @return Set von Roboterpositionen
     */
    public Set<Position> getRoboterPositionen() {
        Set<Position> res = new HashSet<>();
        for (Roboter r : roboterList) {
            res.add(r.getPosition());
        }
        return res;
    }

    /**
     * Gibt an ob eine Position für einen Roboter befahrbar ist
     *
     * @param p zu prüfende Position
     * @param r Roboter der zu Posiiton fahren soll damit eigene Position nicht berücksichtigt wird
     * @return true wenn befahrbar ansonsten false
     */
    public boolean istPositionBefahrbar(Position p, Roboter r) {
        if (p == null) {
            return false;
        }
        Positionsbelegung pb = gitter.getPositionsbelegung(p);
        if (pb.equals(Positionsbelegung.weg) || pb.equals(Positionsbelegung.frei) || r.getPosition().equals(p)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gibt die Abladestationen zurück
     * @return Abladestationen
     */
    public Collection<Abladestation> getAbladestationen() {
        return abladestationen.values();
    }

    /**
     * Gibt die Roboter zurück
     * @return Roboter
     */
    public List<Roboter> getRoboter() {
        return roboterList;
    }

    /**
     * Gibt den Pfad von einer Koordinate zur anderen als Liste zurück
     *
     * @param von  von Position
     * @param nach nach Position
     * @return liste von Koordinaten, die abgefahren werden müssen um ans Ziel zu gelangen
     * @throws KeinWegGefundenException Wirft Exception, wenn kein Weg gefunden wurde
     */
    public ArrayList<Position> getPfadVonNach(Position von, Position nach) throws KeinWegGefundenException {
        return gitter.kuerzesterWegNach(von, nach);
    }


    /**
     * Fügt einen Auftrag hinzu
     * @param auftrag Auftrag
     */
    public void auftragHinzufuegen(Auftrag auftrag) {
        auftragsQueue.add(auftrag);
        verteileUnterauftraege();
    }

    /**
     * Führt das nächste Runnable aus der RunnableQueue aus, falls eines existiert und das vorher ausgeführte terminiert ist
     */
    private void naechstesRunnableAusQueueAusfuehren() {

        if (runnableQueueToExecute.size() > 0 && executorQueue.isEmpty()) {
            execService.execute(runnableQueueToExecute.poll());
        }
    }

    /**
     * Fügt eine Abladestation in das System ein
     * @param abladestation Abladestation zum Hinzufügen
     */
    public void abladestationHinzufuegen(Abladestation abladestation) {
        if (gitter.getPositionsbelegung(abladestation.getPosition()).equals(Positionsbelegung.frei)) {
            abladestationen.put(abladestation.getPosition(), abladestation);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Entfernt eine Abladestation
     * @param abladestation Abladestation zum Entfernen
     */
    public void abladestationEntfernen(Abladestation abladestation) {
        abladestationen.remove(abladestation.getPosition());
    }

    /**
     * Entfernt eine Ladestation aus dem System
     * @param ladestation Ladestation zum Entfernen
     */
    public void ladestationEntfernen(Ladestation ladestation) {
        ladestationen.remove(ladestation.getPosition());
    }

    /**
     * Fügt eine Ladestation in das System ein
     * @param ladestation Ladestation zum Hinzufügen
     */
    public void ladestationHinzufuegen(Ladestation ladestation) {
        if (gitter.getPositionsbelegung(ladestation.getPosition()).equals(Positionsbelegung.frei)) {
            ladestationen.put(ladestation.getPosition(), ladestation);
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Verteilt Unteraufträge an freie Roboter
     */
    private void verteileUnterauftraege() {
        if (!auftragsQueue.isEmpty()) {
            Auftrag tAuftrag = auftragsQueue.peek();
            if (tAuftrag.countObservers() == 0) {
                tAuftrag.addObserver(this);
                uhr.addObserver(tAuftrag);
            }
            for (Roboter r : roboterList) {
                switch (r.getStatus()) {
                    // Wenn Roboter bereit, dann naechsten Unterauftrag ausfuehren
                    default:
                        if (tAuftrag.getUnterauftragsAnzahl() > 0) {
                            try {
                                tAuftrag.naechstenUnterauftragAusfuehren(r);
                            } catch (Exception e) {
                                Logging.log(this.getClass().getName(), Level.WARNING, e.getMessage());
                            }
                        } else {
                            break;
                        }

                        break;
                }
            }
        } else {
            // Keine Aufträge mehr vorhanden. Grid noch einmal neu zeichnen
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Gibt eine Collection mit allen Ladestation im System zurück
     * @return Collection mit allen Ladestationen
     */
    public Collection<Ladestation> getLadestationen() {
        return this.ladestationen.values();
    }

    /**
     * Gibt eine freie Ladestation zurück
     * @return freie Ladestationen
     */
    public Ladestation getFreieLadestation() {
        for (Ladestation ls : ladestationen.values()) {
            if (ls.getStatus() == LadestationStatus.frei) {
                return ls;
            }
        }
        throw new NoSuchElementException("Keine freie Ladestation gefunden");
    }

    /**
     * Gibt eine freie Abladestation zurück
     * @return freie Abladestationen
     */
    public Abladestation getFreieAbladestation() {
        for (Abladestation as : abladestationen.values()) {
            if (as.getStatus() == AbladestationStatus.frei) {
                return as;
            }
        }
        throw new NoSuchElementException("Keine freie Abladestation gefunden");
    }

    /**
     * Gibt den Roboter an einer bestimmten Position zurück
     * @param p Position p
     * @return Roboter an Position p
     * @throws Exception Wirft Exception, wenn kein Roboter gefunden wurde
     */
    public Roboter getRoboterAnPosition(Position p) throws Exception {
        for (Roboter r : roboterList) {
            if (r.getPosition().equals(p)) {
                return r;
            }
        }
        throw new Exception("Kein Roboter an der angegebenen Position");
    }

    /**
     * Erstellt ein Runnable zum verteilen von Unteraufträgen aus dem Auftrag oder zum beenden des Auftrags
     * @param a Auftrag mit zu verteilenden Unteraufträgen oder zum beenden
     * @return Runnable für Auftrag
     */
    private Runnable erstelleAuftragsRunnable(Auftrag a) {
        Runnable runnable = () -> {
            if (a.getStatus() == AuftragsStatus.beendet) {
                // Wenn Auftrag beendet, dann aus der Queue entfernen
                Auftrag auftrag = auftragsQueue.remove();
                auftrag.deleteObservers();
                verteileUnterauftraege();
            } else {
                verteileUnterauftraege();
            }
        };
        return runnable;
    }

    /**
     * Update Routine
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Roboter) {
            Roboter r = (Roboter) o;
            Set<Position> roboterPositionen = getRoboterPositionen();
            gitter.roboterPositionenBereinigen();
            gitter.toKarthesisch(r.getPosition());
            for (Position pos : roboterPositionen) {
                gitter.setPosition(Positionsbelegung.roboter, pos);
            }
            setChanged();
            notifyObservers();

        } else if (o instanceof Auftrag) {
            Auftrag a = (Auftrag) o;
            runnableQueueToExecute.add(erstelleAuftragsRunnable(a));
            naechstesRunnableAusQueueAusfuehren();
        } else if (o instanceof Uhr) {
            naechstesRunnableAusQueueAusfuehren();
            for (Roboter r : roboterList) {
                r.aktualisiereLadestand();
            }

        }
    }
}
