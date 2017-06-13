package gewaechshaus.logic;

import javafx.geometry.Pos;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Roboterleitsystem extends Observable implements Observer {


    private Queue<Auftrag> auftragsQueue;
    private Abladestation abladestation;
    private Ladestation ladestation;
    private Abladestation abladestation2;
    private Map<Position, Roboter> roboterMap;
    private List<Roboter> roboterList;
    private Gitter gitter;


    public Roboterleitsystem(Gitter g) {

        auftragsQueue = new LinkedList<Auftrag>();
        roboterMap = new HashMap<>();
        roboterList = new ArrayList<Roboter>();
        this.gitter = g;

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

    private void verteileUnterauftraege() {
        ArrayList<Roboter> freieRoboter = getFreieRoboter();
        int roboterCount = freieRoboter.size();
        Auftrag tAuftrag = auftragsQueue.peek();
        if (tAuftrag.countObservers() == 0) {
            tAuftrag.addObserver(this);
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

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Pflanzenverwaltung) {

        } else if (o instanceof Roboter) {
            Roboter r = (Roboter) o;
            Position p = getPositionvonRoboter(r);
            gitter.toKarthesisch(r.getPosition());
            setChanged();
            notifyObservers();
        } else if (o instanceof Auftrag) {
            try {
                Auftrag a = (Auftrag) o;
                if (a.getStatus() == AuftragsStatus.beendet) {
                    // Wenn Auftrag beendet, dann aus der Queue entfernen
                    Auftrag p = auftragsQueue.remove();
                    p.deleteObservers();
                    verteileUnterauftraege();
                } else {
                    verteileUnterauftraege();
                }
            } catch (Exception e) {
                Logging.log(this.getClass().getName(), Level.WARNING, "Kein Freier Roboter für Auftragsaufuehrung gefunden!");
            }
        }
    }
}
