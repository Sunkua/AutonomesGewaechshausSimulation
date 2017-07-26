package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.logging.Level;

public class Roboter extends Observable implements Observer {

    private ArrayList<PflanzenArt> pflanzenContainer;
    private RoboterStatus status;
    private Position position;
    private UUID id;

    private Unterauftrag unterauftrag;
    private Akku akku;

    /**
     * Konstruktor für den Roboter
     *
     * @param roboterleitsystem
     */
    public Roboter(Roboterleitsystem roboterleitsystem) {
        pflanzenContainer = new ArrayList<>();
        akku = new Akku(100, Konstanten.kritischeLadeschwelle);
        id = UUID.randomUUID();
    }

    public Unterauftrag getUnterauftrag() {
        return unterauftrag;
    }

    public void setUnterauftrag(Unterauftrag unterauftrag) {
        this.unterauftrag = unterauftrag;
        setChanged();
        notifyObservers();
    }

    public int getFuellstand() {
        return pflanzenContainer.size();
    }

    /**
     * Gibt die UUID des Roboters zurück
     *
     * @return UUID des Roboters
     */
    public UUID getID() {
        return this.id;
    }

    /**
     * Setzt die Geschwindigkeit des Roboters
     *
     * @param geschwindigkeit Geschwindigkeit des Roboters in karthesischem Offset pro Schritt
     */
    public void setGeschwindigkeit(double geschwindigkeit) {
        Konstanten.roboterSchrittweite = geschwindigkeit;
    }

    /**
     * Lädt die Pflanzen an einer Abladestation ab
     *
     * @param abladestation Abladestation an der abgeladen werden soll
     */
    public void ladePflanzenAb(Abladestation abladestation) {
        for (PflanzenArt pa : pflanzenContainer) {
            abladestation.pflanzeAufAbladestationAbladen(pa);
        }
        pflanzenContainer.clear();
    }

    /**
     * Gibt den Akku des Roboters zurück
     *
     * @return Akku
     */
    public Akku getAkku() {
        return akku;
    }

    /**
     * Fährt eine Schrittweite nach oben
     */
    public void fahreNachOben() {
        this.position.setY(this.position.getY() - Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    /**
     * Fährt eine Schrittweite nach unten
     */
    public void fahreNachUnten() {
        this.position.setY(this.position.getY() + Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    /**
     * Fährt eine Schrittweite nach links
     */
    public void fahreNachLinks() {
        this.position.setX(this.position.getX() - Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    /**
     * Fährt eine Schrittweite nach rechts
     */
    public void fahreNachRechts() {
        this.position.setX(this.position.getX() + Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    /**
     * Tue nichts. Warte
     */
    public void warte() {
        Logging.log(this.getClass().getName(), Level.INFO, "Roboter: " + this.getID().toString() + " wartet");
    }

    /**
     * Scanne eine Pflanze an einer Position
     *
     * @param p Gibt gescannten Pflanzenstatus zurück
     * @return
     */
    public PflanzenStatus scanne(Position p) {
        return PflanzenStatus.eReif;
    }

    /**
     * Simuliert das Greifen
     *
     * @return true (Simulation)
     */
    public boolean greife() {
        return true;

    }

    /**
     * Simuliert das Abschneiden
     *
     * @param p Position der Einzelpflanze
     * @return true (Simulation)
     */
    public boolean schneide(Position p) {
        return true;
    }

    /**
     * Lädt die abgeschnittene Pflanze auf
     *
     * @param ep Einzelpflanze, deren Art aufgeladen soll
     * @return true (Simulation)
     */
    public boolean ladePflanzeAuf(Einzelpflanze ep) {
        ep.setPflanzenStatus(PflanzenStatus.eGeerntet);
        pflanzenContainer.add(ep.getArt());
        if (pflanzenContainer.size() >= Konstanten.maximalerFuellstand) {
            setRoboterStatus(RoboterStatus.eBehaelterVoll);
        }
        return true;
    }

    /**
     * Gibt die Position des Roboters zurück
     *
     * @return Position des Roboters
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setzt die Position des Roboters
     *
     * @param position Position des Roboters
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Gibt den Status desRoboters zurück
     *
     * @return Roboterstatus
     */
    public RoboterStatus getStatus() {
        return status;
    }

    /**
     * Setzt den Roboterstatus und benachrichtigt die Observer
     *
     * @param rStatus Roboterstatus
     */
    public void setRoboterStatus(RoboterStatus rStatus) {
        this.status = rStatus;
        hasChanged();
        notifyObservers();
    }

    /**
     * Gibt den Ladestand des Akkus zurück
     *
     * @return Ladestand in Prozent
     */
    public double getLadestand() {
        return akku.getLadestand();
    }

    /**
     * Aktualisiert den Ladestand entsprechend des aktuellen Status des Roboters
     */
    public void aktualisiereLadestand() {
        switch (this.status) {
            case eBeschaeftigt:
                akku.aktualisieren(Konstanten.AkkuEntladungBeschäftigt);
                break;
            case eLädt:
                akku.aktualisieren(Konstanten.AkkuAufladung);
                break;
            default:
                akku.aktualisieren(Konstanten.AkkuEntladungNormal);
                break;
        }
        // Obsolete wenn observer funktioniert..
        if (akku.istKritisch()) {
            setRoboterStatus(RoboterStatus.eAkkuKritisch);
        }

        hasChanged();
        notifyObservers();
    }

    /**
     * Update des Akkus
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Akku) {
            if (akku.istKritisch()) {
                setRoboterStatus(RoboterStatus.eAkkuKritisch);
            }
            if (akku.istLeer()) {
                setRoboterStatus(RoboterStatus.eAkkuLeer);
            }
        }
    }

    /**
     * Gibt den aktuellen Füllstand des Roboters als String zurück
     *
     * @return
     */
    public String getFüllstandString() {
        // TODO Auto-generated method stub
        Double Füllstand = new Double(0);
        for (PflanzenArt p : pflanzenContainer) {
            switch (p) {
                case eTomate:
                    Füllstand += Konstanten.GewichtTomate;
                    break;
                case eGurke:
                    Füllstand += Konstanten.GewichtGurke;
                    break;
                default:
                    break;
            }
        }

        // Immer #####.## kg anzeigen;
        double tmp = Math.round(Füllstand * 100);
        Füllstand = tmp / 100;
        return Füllstand.toString() + " kg";
    }

}
