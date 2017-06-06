package gewaechshaus.fxGUI;

import gewaechshaus.logic.Gitter;
import gewaechshaus.logic.Pflanzenverwaltung;
import gewaechshaus.logic.Positionsbelegung;
import gewaechshaus.logic.Roboterleitsystem;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by sunku on 06.06.2017.
 */
public class FXGewaechshausCanvas extends Canvas {
    private Gitter gitter;
    private int size;
    private GraphicsContext gc;

    public FXGewaechshausCanvas(int size, Gitter g, int breite, int hoehe) {
        super(breite, hoehe);
        this.gitter = g;
        this.size = size;
        gc = this.getGraphicsContext2D();
        paint();

    }

    public void paint() {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        int maxBreite = gitter.getBreite();
        int maxHoehe = gitter.getHoehe();
        for (int i = 0; i < maxBreite; i++) {
            for (int j = 0; j < maxHoehe; j++) {
                switch (gitter.getPositionsbelegung(i, j)) {
                    case frei:
                        gc.setFill(Color.WHITE);
                        gc.strokeRect(i * size, j * size, size, size);
                        break;
                    case pflanze:
                        gc.setFill(Color.GREEN);
                        gc.fillOval(i * size, j * size, size, size);
                        break;
                    case roboter:
                        gc.setFill(Color.RED);
                        gc.fillOval(i * size, j * size, size, size);
                        break;
                    case abladestation:
                        gc.setFill(Color.ORANGE);
                        gc.strokeOval(i * size, j * size, size, size);
                        break;
                    case ladestation:
                        gc.setFill(Color.BLUE);
                        gc.strokeOval(i * size, j * size, size, size);
                        break;
                }

            }
        }
    }
}
