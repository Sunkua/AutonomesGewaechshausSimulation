package gewaechshaus.logic;

import java.util.ArrayList;
import java.util.Observable;
import java.util.UUID;

public class Roboter extends Observable {

    private double batteriestatus;
    private double fuellstand;
    private ArrayList<PflanzenArt> pflanzenContainer;
    private RoboterStatus status;
    private Position position;
    private UUID id;

    private Pflanzenverwaltung pv;
    private Akku akku;


    public Roboter(Roboterleitsystem roboterleitsystem, Pflanzenverwaltung pv) {
        this.pv = pv;
        pflanzenContainer = new ArrayList<>();
        akku = new Akku(100,10);
        id = UUID.randomUUID();
    }

    public UUID getID() {
        return this.id;
    }

    public void setGeschwindigkeit(double geschwindigkeit) {
        Konstanten.roboterSchrittweite = geschwindigkeit;
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
        pv.pflanzeEntfernen(ep.getPosition());
        pflanzenContainer.add(ep.getArt());
        return true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    private void setFuellstand(double fuellstand) {
    	this.fuellstand = fuellstand;
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
    	
    	if(akku.istKritisch()){
    		//ToDo
    		//Logging.log(this.getClass().getSimpleName(), Level.WARNING, this.getClass().getSimpleName()+" Akkuladung ist kritisch");
    	}
    	if(akku.istLeer()){
    		// ToDo
    	}
        //hasChanged();
        //notifyObservers();
    }


}
