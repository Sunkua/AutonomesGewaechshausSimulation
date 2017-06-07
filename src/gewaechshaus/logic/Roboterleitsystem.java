package gewaechshaus.logic;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;
import java.util.logging.Level;


@XmlRootElement(namespace = "gewaeshaus.logic")
public class Roboterleitsystem extends Observable implements Observer {



    private Stack<Auftrag> auftragsStack;
    private Abladestation abladestation;
    private Ladestation ladestation;
    private Abladestation abladestation2;
    private Map<Position, Roboter> roboterMap;
    private Gitter gitter;


    public Roboterleitsystem(Gitter g)  {

        auftragsStack = new Stack<Auftrag>();
        roboterMap = new HashMap<>();
        this.gitter = g;
        
        Logging.log(this.getClass().getSimpleName(), Level.CONFIG, this.getClass().getSimpleName()+" geladen");
    }


    public Set<Position> getRoboterPositionen() {
        return roboterMap.keySet();
    }

    public ArrayList<Position> getPfadVonNach(Position a, Position b) {
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
            setChanged();
            notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    public void fahreRoboterZu(Roboter r, Position p) {
      if(r.getStatus() == RoboterStatus.eBereit) {
          r.fahreZu(p);
      }

    }


    public Unterauftrag getUnterauftrag(int ID) {
        return null;

    }


    public void setRoboterStatus(Roboter roboter, RoboterStatus status) {

    }


    public void auftragHinzufuegen(Auftrag auftrag) {
        auftragsStack.push(auftrag);

    }

    public Roboter getRoboterAnPosition(Position p) {
        return roboterMap.get(p);
    }


    private void warte() {

    }

    public Position getPositionvonRoboter(Roboter r ) {
        Set<Map.Entry<Position, Roboter>> rPosCollection = this.roboterMap.entrySet();
        for(Map.Entry<Position, Roboter> roboP : rPosCollection) {
            if(roboP.getValue().equals(r)) {
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
        }
    }
}
