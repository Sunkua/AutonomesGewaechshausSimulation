package gewaechshaus.fxGUI;

import gewaechshaus.gui.GewächshausCanvas;
import gewaechshaus.logic.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class FXGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {


        // TODO Auto-generated method stub
        Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung();
        pVerwaltung.setMaxGröße(10, 10);
        Gitter gitter = new Gitter(10f, 10f, 10, 10);
        Roboterleitsystem leitSystem = new Roboterleitsystem(gitter);
        Auftragsgenerator auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, gitter);

        pVerwaltung.addObserver(leitSystem);

        pVerwaltung.addObserver(gitter);
        leitSystem.addObserver(gitter);

        Roboter r = new Roboter(leitSystem);

        Position roboPos = new Position(5f, 5f);
        gitter.toKarthesisch(roboPos);
        leitSystem.roboterHinzufuegen(r, roboPos);

        r.addObserver(leitSystem);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Set width and height
        Scene scene = new Scene(grid, 500, 200, Color.BLACK);

        // Left Pane
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 1, 1);

        Separator titleSeparator = new Separator();
        grid.add(titleSeparator, 0, 2, 2, 2);

        // Left-Right separator
        Separator leftRightSeparator = new Separator();
        leftRightSeparator.setOrientation(Orientation.VERTICAL);
        grid.add(leftRightSeparator, 2, 2, 1, 10);


        Aktionsgrid interaktionsGrid = new Aktionsgrid();

        grid.add(interaktionsGrid, 3, 2);

        // Canvas-Building, Event-Listeners redraw on rescale
        FXGewaechshausCanvas canvas = new FXGewaechshausCanvas((int) Math.round(scene.getWidth() / 10), gitter, 500, 500, pVerwaltung, leitSystem);
        grid.add(canvas, 0, 3, 2, 2);
        pVerwaltung.addObserver(canvas);
        leitSystem.addObserver(canvas);

        // Stage building
        stage.setScene(scene);
        stage.setTitle("Gewächshaus-Roboter");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth() - 150);
        stage.setHeight(primaryScreenBounds.getHeight() - 150);

        TextField zeile = new TextField();
        TextField spalte = new TextField();
        Label lbZeile = new Label("Zeile");
        Label lbSpalte = new Label("Spalte");

        grid.add(lbZeile, 0, 6);
        grid.add(zeile, 0, 7);
        grid.add(lbSpalte, 1, 6);
        grid.add(spalte, 1, 7);


        Button testfahrt = new Button("Gurken ernten");
        testfahrt.setOnAction(
                e -> {
                    int x = Integer.parseInt(spalte.getText());
                    int y = Integer.parseInt(zeile.getText());
                    Auftrag gurkenErnten = auftragsgenerator.pflanzenVonArtErnten(PflanzenArt.eGurke);
                    leitSystem.auftragHinzufuegen(gurkenErnten);
                    Position ziel = new Position((double) x, (double) y);
                    gitter.toKarthesisch(ziel);
                    Runnable task = () -> {
                        if (r.getStatus() == RoboterStatus.eBereit) {

                        }
                    };
                    Thread thread = new Thread(task);
                    thread.start();
                });
        grid.add(testfahrt, 0, 5);

        for (int i = 0; i < gitter.getBreite(); i++) {
            for (int j = 0; j < gitter.getHoehe(); j++) {
                if (i % 5 != 0 && j % 3 != 0) {
                    Einzelpflanze t = new Einzelpflanze(PflanzenArt.eGurke, new Position(i, j), 0.5, PflanzenStatus.eReif, null);
                    pVerwaltung.pflanzeHinzufuegen(t);
                }
            }
        }

        Einzelpflanze t = new Einzelpflanze(PflanzenArt.eGurke, new Position(5, 4), 0.5, PflanzenStatus.eReif, null);
        pVerwaltung.pflanzeHinzufuegen(t);


        stage.show();
    }


}