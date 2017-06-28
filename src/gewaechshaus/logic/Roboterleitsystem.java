package gewaechshaus.logic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Roboterleitsystem extends Observable implements Observer {


    private Queue<Auftrag> auftragsQueue;
    private Abladestation abladestation;
    private Ladestation ladestation;
    private Abladestation abladestation2;
    private Map<Position, Roboter> roboterMap;
    private List<Roboter> roboterList;
    private Gitter gitter;
    private LinkedBlockingQueue<Runnable> runnableQueueToExecute;
    private LinkedBlockingQueue<Runnable> executorQueue;
    private ExecutorService execService;
    private Clock clock;

    public Roboterleitsystem(Gitter g, Clock clock) {
        this.clock = clock;
        auftragsQueue = new LinkedList<Auftrag>();
        roboterMap = new HashMap<>();
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
    public List<Roboter> getRoboter(){
    	return roboterList;
    }

    public ArrayList<Position> getPfadVonNach(Position a, Position b) throws NoWayFoundException {
        return gitter.kuerzesterWegNach(a, b);
    }

    public boolean operationAbbrechen(int ID) {

        return true;
    }

    public void abladeStationDefinieren() {

    }

    public boolean roboterHinzufuegen(Roboter r, Position p) {
        if (gitter.getPositionsbelegung(p) == Positionsbelegung.frei) {
            roboterList.add(r);
            r.setPosition(p);
            r.setRoboterStatus(RoboterStatus.eBereit);
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

    private void warte() {

    }

    public Position getPositionvonRoboter(Roboter r) {
        return roboterList.get(roboterList.indexOf(r)).getPosition();
    }

    private void roboterGitterReset() {

    }


    private void sendeMeldung() {

    }

    private Runnable erstelleRoboterRunnable(Roboter roboter) {
        Runnable runnable = () -> {
            Position p = getPositionvonRoboter(roboter);
            gitter.toKarthesisch(p);
            setChanged();
            notifyObservers();
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
            runnableQueueToExecute.add(erstelleRoboterRunnable(r));
            naechstesRunnableAusQueueAusfuehren();
        } else if (o instanceof Auftrag) {
            Auftrag a = (Auftrag) o;
            runnableQueueToExecute.add(erstelleAuftragsRunnable(a));
            naechstesRunnableAusQueueAusfuehren();
        } else if (o instanceof Clock) {
            naechstesRunnableAusQueueAusfuehren();
            for (Roboter r : roboterList) {
            	r.aktualisiereLadestand();
            }
            
        }
    }
}
