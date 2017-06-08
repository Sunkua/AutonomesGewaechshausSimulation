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
    private Gitter gitter;


    public Roboterleitsystem(Gitter g) {

        auftragsQueue = new LinkedList<Auftrag>();
        roboterMap = new HashMap<>();
        this.gitter = g;

        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName() + " geladen");
    }


    public Collection<Position> getFreieNachbarFelderVon(Position p) {
        return gitter.getFreieNachbarFelder(p);
    }

    public Set<Position> getRoboterPositionen() {
        return roboterMap.keySet();
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
            roboterMap.put(p, r);
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
        Collection<Roboter> freieRoboter = getFreieRoboter();
        int roboterCount = freieRoboter.size();
        Auftrag tAuftrag = auftragsQueue.peek();
        while (tAuftrag.getUnterauftragsAnzahl() > 0 && getFreieRoboter().size() > 0) {

        }
    }

    private Collection<Roboter> getFreieRoboter() {
        return this.roboterMap.entrySet().stream()
                .filter(map -> map.getValue().getStatus().equals(RoboterStatus.eBereit))
                .map(map -> map.getValue())
                .collect(Collectors.toList());
    }

    public Roboter getRoboterAnPosition(Position p) {
        return roboterMap.get(p);
    }

    private void warte() {

    }

    public Position getPositionvonRoboter(Roboter r) {
        Set<Map.Entry<Position, Roboter>> rPosCollection = this.roboterMap.entrySet();
        for (Map.Entry<Position, Roboter> roboP : rPosCollection) {
            if (roboP.getValue().equals(r)) {
                return roboP.getKey();
            }
        }
        return null;
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
            this.roboterMap.remove(p);
            this.roboterMap.put(r.getPosition(), r);
            setChanged();
            notifyObservers();
        } else if (o instanceof Clock) {

        }
    }
}
