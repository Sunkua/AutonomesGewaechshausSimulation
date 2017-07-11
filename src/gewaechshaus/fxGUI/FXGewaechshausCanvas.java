package gewaechshaus.fxGUI;

import gewaechshaus.logic.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Created by sunku on 06.06.2017.
 */
public class FXGewaechshausCanvas extends Canvas implements Observer {
    private Gitter gitter;
    private int size;
    private GraphicsContext gc;
    private Pflanzenverwaltung pflanzenverwaltung;
    private Roboterleitsystem roboterleitsystem;

    public FXGewaechshausCanvas(int size, Gitter g, int breite, int hoehe, Pflanzenverwaltung pflanzenverwaltung, Roboterleitsystem roboterleitsystem) {
        super(breite, hoehe);
        this.gitter = g;
        this.size = size;
        this.roboterleitsystem = roboterleitsystem;
        this.pflanzenverwaltung = pflanzenverwaltung;
        gc = this.getGraphicsContext2D();
        paint();
    }

    public void paint() {

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        int maxBreite = gitter.getBreite();
        int maxHoehe = gitter.getHoehe();
        for (int i = 0; i < maxBreite; i++) {
            for (int j = 0; j < maxHoehe; j++) {
                gc.strokeRect(i * size, j * size, size, size);
            }
        }

        Map<Position, Einzelpflanze> pflanzen = pflanzenverwaltung.getAllePflanzen();
        for (Map.Entry<Position, Einzelpflanze> pflanze : pflanzen.entrySet()) {
            int spalte = pflanze.getKey().getSpaltenID();
            int reihe = pflanze.getKey().getReihenID();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeOval(spalte * size + 1, reihe * size + 1, size - 2, size - 2);
            switch (pflanze.getValue().getArt()) {
                case eTomate:
                    gc.setFill(Color.RED);
                    break;
                case eGurke:
                    gc.setFill(Color.GREEN);
                    break;
            }
            double reife = pflanze.getValue().getReifestatus() / 100;
            int pflanzengröße = (int) ((size - 4) * reife);
            int offset = (size - 4 - pflanzengröße) / 2;
            gc.fillOval(spalte * size + 2 + offset, reihe * size + 2 + offset, pflanzengröße, pflanzengröße);

        }

        // Draw Roboter
        Set<Position> roboterPos = roboterleitsystem.getRoboterPositionen();
        for (Position p : roboterPos) {
            gc.setFill(Color.BLUE);
            gc.fillRect(p.getSpaltenID() * size, p.getReihenID() * size, size, size);
        }

        for (Abladestation al : roboterleitsystem.getAbladestationen()) {
            gc.setFill(Color.SADDLEBROWN);
            gc.fillRect(al.getPosition().getSpaltenID() * size, al.getPosition().getReihenID() * size, size, size);
        }

        for (Ladestation ls : roboterleitsystem.getLadestationen()) {
            gc.setFill(Color.YELLOW);
            gc.fillRect(ls.getGridPosition().getSpaltenID() * size, ls.getGridPosition().getReihenID() * size, size, size);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        if (o instanceof Uhr) {

            gc.clearRect(0, 0, this.getWidth(), this.getHeight());
            this.paint();
        }
    }

}
