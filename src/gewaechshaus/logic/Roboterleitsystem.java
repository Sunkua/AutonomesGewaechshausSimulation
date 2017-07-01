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
    private Map<Roboter, Position> vorherigeRoboterPositionen;
    private List<Roboter> roboterList;
    private Gitter gitter;
    private LinkedBlockingQueue<Runnable> runnableQueueToExecute;
    private LinkedBlockingQueue<Runnable> executorQueue;
    private ExecutorService execService;
    private Clock clock;

    public Roboterleitsystem(Gitter g, Clock clock) {
        this.clock = clock;
        auftragsQueue = new LinkedList<Auftrag>();
        vorherigeRoboterPositionen = new HashMap<>();
        abladestationen = new HashMap<>();
        ladestationen = new HashMap<>();
        roboterList = new ArrayList<Roboter>();
        this.gitter = g;
        runnableQueueToExecute = new LinkedBlockingQueue<>();
        executorQueue = new LinkedBlockingQueue<Runnable>();
        execService = new ThreadPoolExecutor(1, 1,
                10, TimeUnit.MILLISECONDS,
                executorQueue);

        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }


    public List<Position> getFreieNachbarFelderVon(Position p) {
        List<Position> posListe = gitter.getFreieNachbarFelder(p);
        return posListe;
    }

    public Set<Position> getRoboterPositionen() {
        Set<Position> res = new HashSet<>();
        for (Roboter r : roboterList) {
            res.add(r.getPosition());
        }
        return res;
    }

    public Collection<Abladestation> getAbladestationen() {
        return abladestationen.values();
    }

    public List<Roboter> getRoboter() {
        return roboterList;
    }

    public ArrayList<Position> getPfadVonNach(Position a, Position b) throws KeinWegGefundenException {
        return gitter.kuerzesterWegNach(a, b);
    }

    public boolean operationAbbrechen(int ID) {

        return true;
    }


    public boolean roboterHinzufuegen(Roboter r, Position p) {
        if (gitter.getPositionsbelegung(p) == Positionsbelegung.frei) {
            roboterList.add(r);
            r.setPosition(p);
            r.setRoboterStatus(RoboterStatus.eBereit);
            r.addObserver(this);
            setChanged();
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public Unterauftrag getUnterauftrag(int ID) {
        return null;
    }

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

    public void abladestationHinzufuegen(Abladestation abladestation) {
        if (gitter.getPositionsbelegung(abladestation.getGridPosition()).equals(Positionsbelegung.frei)) {
            abladestationen.put(abladestation.getGridPosition(), abladestation);
            setChanged();
            notifyObservers();
        }
    }

    public void abladestationEntfernen(Abladestation abladestation) {
        abladestationen.remove(abladestation.getGridPosition());
    }

    public void ladestationEntfernen(Ladestation ladestation) {
        ladestationen.remove(ladestation.getGridPosition());
    }

    public void ladestationHinzufuegen(Ladestation ladestation) {
        if (gitter.getPositionsbelegung(ladestation.getGridPosition()).equals(Positionsbelegung.frei)) {
            ladestationen.put(ladestation.getGridPosition(), ladestation);
            setChanged();
            notifyObservers();
        }
    }

    private void verteileUnterauftraege() {
        ArrayList<Roboter> freieRoboter = getFreieRoboter();
        int roboterCount = freieRoboter.size();
        Auftrag tAuftrag = auftragsQueue.peek();
        if (tAuftrag.countObservers() == 0) {
            tAuftrag.addObserver(this);
            clock.addObserver(tAuftrag);
        }

        for (Roboter r : freieRoboter) {
            if (tAuftrag.getUnterauftragsAnzahl() > 0) {
                try {
                    tAuftrag.naechstenUnterauftragAusfuehren(r);
                    tAuftrag.setMaxAktiveUnterauftraege(roboterList.size());
                } catch (Exception e) {
                    Logging.log(this.getClass().getName(), Level.WARNING, e.getMessage());
                }
            } else {
                break;
            }
        }
    }

    private ArrayList<Roboter> getFreieRoboter() {
        ArrayList<Roboter> freieRoboter = new ArrayList<>();
        for (Roboter r : roboterList) {
            if (r.getStatus().equals(RoboterStatus.eBereit)) {
                freieRoboter.add(r);
            }
        }
        return freieRoboter;
    }

    public Roboter getRoboterAnPosition(Position p) throws Exception {
        for (Roboter r : roboterList) {
            if (r.getPosition().equals(p)) {
                return r;
            }
        }
        throw new Exception("Kein Roboter an der angegebenen Position");
    }


    public Position getPositionvonRoboter(Roboter r) {
        return roboterList.get(roboterList.indexOf(r)).getPosition();
    }

    private Runnable erstelleRoboterRunnable(Roboter roboter) {
        Runnable runnable = () -> {

        };
        return runnable;
    }

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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Pflanzenverwaltung) {

        } else if (o instanceof Roboter) {
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
            if (a.getStatus() == AuftragsStatus.beendet) {
                // Wenn Auftrag beendet, dann aus der Queue entfernen
                Auftrag auftrag = auftragsQueue.remove();
                auftrag.deleteObservers();
                verteileUnterauftraege();
            } else {
                verteileUnterauftraege();
            }
            //runnableQueueToExecute.add(erstelleAuftragsRunnable(a));
            //naechstesRunnableAusQueueAusfuehren();
        } else if (o instanceof Clock) {
            //  naechstesRunnableAusQueueAusfuehren();
            for (Roboter r : roboterList) {
                r.aktualisiereLadestand();
            }

        }
    }
}
