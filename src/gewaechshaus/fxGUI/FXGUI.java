package gewaechshaus.fxGUI;

import gewaechshaus.logic.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
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

import java.util.logging.Level;


public class FXGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {


        // TODO Auto-generated method


        Gitter gitter = new Gitter(12f, 12f, 12, 12);

        Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung(gitter);

        Uhr uhr = new Uhr(200);
        Roboterleitsystem leitSystem = new Roboterleitsystem(gitter, uhr);
        Auftragsgenerator auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, gitter, uhr);
        Position abladestelle = new Position(11f, 11f);
        gitter.toKarthesisch(abladestelle);
        Abladestation abladestation = new Abladestation(abladestelle);

        Position ladestelle = new Position(10f, 11f);
        gitter.toKarthesisch(ladestelle);
        Ladestation ladestation = new Ladestation(ladestelle);


        pVerwaltung.addObserver(leitSystem);
        pVerwaltung.addObserver(gitter);
        leitSystem.addObserver(gitter);
        uhr.addObserver(leitSystem);
        uhr.addObserver(pVerwaltung);



        leitSystem.abladestationHinzufuegen(abladestation);
        leitSystem.ladestationHinzufuegen(ladestation);

        // Roboter hinzufügen
        leitSystem.roboterHinzufuegen(pVerwaltung);
        leitSystem.roboterHinzufuegen(pVerwaltung);
        leitSystem.roboterHinzufuegen(pVerwaltung);

        EigenschafteGrid eigenschaftsgrid = new EigenschafteGrid(leitSystem);
        for (Roboter r : leitSystem.getRoboter()) {
            r.addObserver(eigenschaftsgrid);
        }
        abladestation.addObserver(eigenschaftsgrid);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Left Pane
        Text scenetitle = new Text("Gewächshausverwaltungssoftware");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0);

        Separator titleSeparator = new Separator();
        grid.add(titleSeparator, 0, 2, 2, 1);

        // Set width and height
        Scene scene = new Scene(grid, 500, 500, Color.BLACK);

        // Left-Right separator
        Separator leftRightSeparator = new Separator();
        leftRightSeparator.setOrientation(Orientation.VERTICAL);
        grid.add(leftRightSeparator, 2, 2, 1, 10);


        Aktionsgrid interaktionsGrid = new Aktionsgrid();

        grid.add(interaktionsGrid, 3, 3);
        grid.add(eigenschaftsgrid, 3, 4);

        // Canvas-Building, Event-Listeners redraw on rescale
        FXGewaechshausCanvas canvas = new FXGewaechshausCanvas((int) Math.round(scene.getWidth() / 10), gitter, 700, 700, pVerwaltung, leitSystem);
        grid.add(canvas, 0, 3, 2, 2);

        uhr.addObserver(canvas);

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


        Button gurkenErnte = new Button("Gurken ernten");
        gurkenErnte.setOnAction(
                e -> {
                    Auftrag gurkenErnten = auftragsgenerator.pflanzenVonArtErnten(PflanzenArt.eGurke);
                    leitSystem.auftragHinzufuegen(gurkenErnten);
                });
        grid.add(gurkenErnte, 0, 8);

        Button simulationsSchritt = new Button("Simulationsschritt");
        simulationsSchritt.setOnAction(e -> {
            uhr.schritt();
        });
        grid.add(simulationsSchritt, 0, 9);

        Button timerStart = new Button("Simulationsuhr starten");
        Button timerStop = new Button("Simulationsuhr anhalten");
        Button timerPeriodeAktualisieren = new Button("Timer-Periodendauer setzen");
        NummerFeld simulationsPeriode = new NummerFeld();

        timerStart.setOnAction(e -> uhr.startTimer());
        timerStop.setOnAction(e -> uhr.stopTimer());
        timerPeriodeAktualisieren.setOnAction(e -> {
            int schrittZeit = Integer.parseInt(simulationsPeriode.getText());
            if (schrittZeit > 0)
                uhr.setSchrittZeit(schrittZeit);

        });


        grid.add(timerStart, 0, 10);
        grid.add(timerStop, 1, 10);
        grid.add(timerPeriodeAktualisieren, 1, 11);
        grid.add(simulationsPeriode, 0, 11);


        // Pflanzen hinzufügen
        for (int i = 0; i < 5; i++) {
            try {
                pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
                pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eGurke);
            } catch (Exception e) {
                Logging.log(this.getClass().getName(), Level.WARNING, "Keine freie Pflanzenposition gefunden");
            }
        }

        //  Einzelpflanze t = new Einzelpflanze(PflanzenArt.eGurke, new Position(5, 4), 0.5, PflanzenStatus.eReif, null);
        //pVerwaltung.pflanzeHinzufuegen(t);

        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        canvas.paint();
        stage.show();

    }
}