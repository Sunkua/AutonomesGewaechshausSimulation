package gewaechshaus.fxGUI;

import gewaechshaus.logic.*;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Set;

/**
 * Created by sunku on 06.06.2017.
 */
public class FXGewaechshausCanvas extends Canvas {
    private Gitter gitter;
    private int size;
    private GraphicsContext gc;
    private Pflanzenverwaltung pflanzenverwaltung;
    private Roboterleitsystem roboterleitsystem;
    private Image tomatenBild;
    private Image gurkenBild;
    private Image roboterBild;
    private Image wegBild;
    private Image ladeStationBild;
    private Image abladeStationBild;
    private AnimationTimer animationsTimer;

    public FXGewaechshausCanvas(int size, Gitter g, int breite, int hoehe, Pflanzenverwaltung pflanzenverwaltung, Roboterleitsystem roboterleitsystem) {


        super(breite, hoehe);


        animationsTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.clearRect(0, 0, getWidth(), getHeight());
                paint();
            }
        };
        this.gitter = g;
        this.size = size;
        this.roboterleitsystem = roboterleitsystem;
        this.pflanzenverwaltung = pflanzenverwaltung;
        tomatenBild = new Image("gewaechshaus/images/tomate.png");
        gurkenBild = new Image("gewaechshaus/images/gurke.png");
        roboterBild = new Image("gewaechshaus/images/roboter.png");
        wegBild = new Image("gewaechshaus/images/weg.png");
        ladeStationBild = new Image("gewaechshaus/images/ladestation.png");
        abladeStationBild = new Image("gewaechshaus/images/abladestation.png");

        gc = this.getGraphicsContext2D();
        animationsTimer.start();

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
            double reife = pflanze.getValue().getReifestatus() / 100;
            int pflanzengröße = (int) ((size - 4) * reife);
            int offset = (size - 4 - pflanzengröße) / 2;

            int spalte = pflanze.getKey().getSpaltenID();
            int reihe = pflanze.getKey().getReihenID();
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            switch (pflanze.getValue().getArt()) {
                case eTomate:
                    gc.drawImage(tomatenBild, spalte * size + 2 + offset, reihe * size + 2 + offset, pflanzengröße, pflanzengröße);
                    //gc.setFill(Color.RED);
                    break;
                case eGurke:
                    gc.drawImage(gurkenBild, spalte * size + 2 + offset, reihe * size + 2 + offset, pflanzengröße, pflanzengröße);
                    break;
            }
        }

        // Draw Roboter
        Set<Position> roboterPos = roboterleitsystem.getRoboterPositionen();
        for (Position p : roboterPos) {
            // gc.setFill(Color.BLUE);
            //gc.fillRect(p.getSpaltenID() * size, p.getReihenID() * size, size, size);
            gc.drawImage(wegBild, p.getSpaltenID() * size, p.getReihenID() * size, size, size);
            gc.drawImage(roboterBild, p.getSpaltenID() * size, p.getReihenID() * size, size, size);
        }

        for (Abladestation al : roboterleitsystem.getAbladestationen()) {
            // gc.setFill(Color.SADDLEBROWN);
            gc.drawImage(abladeStationBild, al.getPosition().getSpaltenID() * size, al.getPosition().getReihenID() * size, size, size);
            //gc.fillRect(al.getPosition().getSpaltenID() * size, al.getPosition().getReihenID() * size, size, size);
        }

        for (Ladestation ls : roboterleitsystem.getLadestationen()) {
            //  gc.setFill(Color.YELLOW);
            gc.drawImage(ladeStationBild, ls.getPosition().getSpaltenID() * size, ls.getPosition().getReihenID() * size, size, size);
            //  gc.fillRect(ls.getGridPosition().getSpaltenID() * size, ls.getGridPosition().getReihenID() * size, size, size);
        }
        for (Position p : gitter.getWege()) {
            gc.drawImage(wegBild, p.getSpaltenID() * size, p.getReihenID() * size, size, size);
        }

    }


}
