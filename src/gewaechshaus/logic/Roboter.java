package gewaechshaus.logic;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Roboter extends Observable {

    private double batteriestatus;
    private double fuellstand;
    private RoboterStatus status;
    private Position position;
    private double schrittweite = 0.5f;

    public Roboter(Roboterleitsystem roboterleitsystem) {
    }


    public void setGeschwindigkeit(double geschwindigkeit) {
        this.schrittweite = geschwindigkeit;
    }

    public void fahreNachOben() {
        this.position.setY(this.position.getY() - schrittweite);
        setChanged();
        notifyObservers();
    }

    public void fahreNachUnten() {
        this.position.setY(this.position.getY() + schrittweite);
        setChanged();
        notifyObservers();
    }

    public void fahreNachLinks() {
        this.position.setX(this.position.getX() - schrittweite);
        setChanged();
        notifyObservers();
    }

    public void fahreNachRechts() {
        this.position.setX(this.position.getX() - schrittweite);
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
        return true;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    private void setFuellstand() {

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


}
