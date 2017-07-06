package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.logging.Level;

public class Roboter extends Observable implements Observer {

    private double batteriestatus;
    private ArrayList<PflanzenArt> pflanzenContainer;
    private RoboterStatus status;
    private Position position;
    private Position vorherigePosition;
    private UUID id;

    private Pflanzenverwaltung pv;
    private Akku akku;


    public Roboter(Roboterleitsystem roboterleitsystem, Pflanzenverwaltung pv) {
        this.pv = pv;
        pflanzenContainer = new ArrayList<>();
        akku = new Akku(100, 90);
        id = UUID.randomUUID();
    }
    public void AddAkku( Akku a){
    	akku = a;
    	akku.addObserver(this); // geht nicht .. warum auch immer..
    }

    public UUID getID() {
        return this.id;
    }


    public void setGeschwindigkeit(double geschwindigkeit) {
        Konstanten.roboterSchrittweite = geschwindigkeit;
    }

    public void ladePflanzenAb(Abladestation as) {
        for (PflanzenArt pa : pflanzenContainer) {
            as.pflanzeAufAbladestationAbladen(pa);
        }
    }

    public Akku getAkku() {
        return akku;
    }


    public void fahreNachOben() {
        this.position.setY(this.position.getY() - Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    public void fahreNachUnten() {
        this.position.setY(this.position.getY() + Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    public void fahreNachLinks() {
        this.position.setX(this.position.getX() - Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    public void fahreNachRechts() {
        this.position.setX(this.position.getX() + Konstanten.roboterSchrittweite);
        setChanged();
        notifyObservers();
    }

    public void warte() {
        Logging.log(this.getClass().getName(), Level.INFO, "Roboter: " + this.getID().toString() + " wartet");
    }


    public PflanzenStatus scanne(Position p) {
        return PflanzenStatus.eReif;
    }

    public boolean greife() {
        return true;

    }

    public boolean schneide(Position p) {
        return true;
    }

    public boolean ladePflanzeAuf(Einzelpflanze ep) {
    	ep.setPflanzenstatus(PflanzenStatus.eGeerntet);
        pflanzenContainer.add(ep.getArt());
        if (pflanzenContainer.size() >= Konstanten.maximalerFuellstand) {
            setRoboterStatus(RoboterStatus.eBehaelterVoll);
        }
        return true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    public void setAuftrag(Unterauftrag unterauftrag) {

    }

    public RoboterStatus getStatus() {
        return status;
    }

    public void setRoboterStatus(RoboterStatus rStatus) {
        this.status = rStatus;
        hasChanged();
        notifyObservers();
    }
    
    public double getLadestand(){
    	return akku.getLadestand();
    }
    
    public void aktualisiereLadestand(){
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
    
    @Override
    public void update(Observable o, Object arg) {
    	if( o instanceof Akku){
	        if (akku.istKritisch()) {
	            setRoboterStatus(RoboterStatus.eAkkuKritisch);
	        }
	        if (akku.istLeer()) {
	            setRoboterStatus(RoboterStatus.eAkkuLeer);            	
	        }
    	}
	}
	public String getFüllstand() {
		// TODO Auto-generated method stub
		Double Füllstand = new Double(0);
		for (PflanzenArt p : pflanzenContainer){
			switch(p){
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
		
		// always show #####.## kg;
		double tmp = Math.round(Füllstand * 100);
		Füllstand = tmp / 100;
		return Füllstand.toString() + " kg";
	}
    	


}
