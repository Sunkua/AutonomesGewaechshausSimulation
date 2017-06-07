package gewaechshaus.logic;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

public class Roboter extends Observable implements Observer {

    private double batteriestatus;
    private double fuellstand;
    private RoboterStatus status;
    private Roboterleitsystem roboterleitsystem;
    private Position position;
    private static double schrittweite = 0.5f;
    private boolean canStep = true;
    private boolean stepTest;

    public Roboter(Roboterleitsystem roboterleitsystem) {
        this.roboterleitsystem = roboterleitsystem;
        stepTest = false;

    }

    public boolean GeheZu(Position RelativePos) {


        return false;
    }

    public boolean fahreSchritt(Position zielPosition) {

        double xOffset = this.position.getX() - zielPosition.getX();
        double yOffset = this.position.getY() - zielPosition.getY();
        if (zielPosition.getReihenID() - this.position.getReihenID() == 0) {

            if (xOffset > 0) {
                this.position.setX(this.position.getX() - schrittweite);
            } else {
                this.position.setX(this.position.getX() + schrittweite);
            }

        } else if (zielPosition.getSpaltenID() - this.position.getSpaltenID() == 0) {

            if (yOffset > 0) {
                this.position.setY(this.position.getY() - schrittweite);
            } else {
                this.position.setY(this.position.getY() + schrittweite);
            }

        }
        setChanged();
        notifyObservers();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        return this.position.equals(zielPosition);
    }


    public void fahreZu(Position zielPosition) {

        ArrayList<Position> pfad = roboterleitsystem.getPfadVonNach(this.position, zielPosition);
        Collections.reverse(pfad);
        while (!this.position.equals(zielPosition)) {
            while (!this.position.equals(pfad.get(0))) {
                stepTest = fahreSchritt(pfad.get(0));

            }
            pfad.remove(0);

        }
    }

    public PflanzenStatus scanne(Position p) {
        return PflanzenStatus.eReif;
    }


    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean greife() {
        return true;

    }

    public boolean schneide() {
        return true;

    }

    public boolean lade_Auf() {
        return true;
    }


    public Position getPosition() {
        return position;
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


    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Clock) {

        }
    }
}
