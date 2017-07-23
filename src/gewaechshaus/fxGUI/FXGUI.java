package gewaechshaus.fxGUI;

import gewaechshaus.logic.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Auftragsgenerator auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, uhr);
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
        // Set width and height
        Scene scene = new Scene(grid, 400, 400, Color.BLACK);
        // Left Pane
        Text scenetitle = new Text("Gewächshausverwaltungssoftware");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0);
        //   Aktionsgrid interaktionsGrid = new Aktionsgrid();




        // Canvas-Building, Event-Listeners redraw on rescale
        FXGewaechshausCanvas canvas = new FXGewaechshausCanvas((int) Math.round(scene.getWidth() / 10), gitter, 500, 500, pVerwaltung, leitSystem);
        grid.add(canvas, 0, 4, 1, 2);

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


        GridPane simulationsGrid = new GridPane();
        simulationsGrid.setHgap(10);
        simulationsGrid.setVgap(10);
        simulationsGrid.setPadding(new Insets(25, 25, 25, 25));

        Button simulationsSchritt = new Button("Simulationsschritt");
        simulationsSchritt.setOnAction(e -> {
            uhr.tick();
        });


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


        simulationsGrid.add(timerStart, 0, 0);
        simulationsGrid.add(timerStop, 1, 0);
        simulationsGrid.add(timerPeriodeAktualisieren, 0, 2);
        simulationsGrid.add(simulationsPeriode, 1, 2);
        simulationsGrid.add(simulationsSchritt, 0, 3);


        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Gurken",
                        "Tomaten"
                );
        ComboBox pflanzenAuswahl = new ComboBox(options);
        pflanzenAuswahl.setValue("Gurken");

        // Pflanze hinzufügen
        Button pflanzeHinzufuegen = new Button("Pflanze Hinzufügen");
        pflanzeHinzufuegen.setOnAction(e -> {
            String comboboxWert = (String) pflanzenAuswahl.getValue();
            try {
                switch (comboboxWert) {
                    case "Gurken":
                        pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eGurke);
                        canvas.paint();
                        break;
                    case "Tomaten":
                        pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
                        canvas.paint();
                        break;
                    default:
                        Logging.log(this.getClass().getName(), Level.WARNING, "ungültiger Wert aus Combobox für das Hinzufügen ausgewählt");
                        break;
                }
            } catch (Exception ex) {
                // Hier Info-Dialog einfügen
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Achtung, es konnte keine Pflanze hinzugefügt werden, da das Gewächshaus über keine weiteren leeren Beete verfügt");
                alert.showAndWait();
                Logging.log(this.getClass().getName(), Level.WARNING, "Pflanze konnte nicht hinzugefügt werden");
            }
        });


        // Reife Pflanzen ernten
        Button reifePflanzenErnten = new Button("Reife Pflanzen ernten");
        reifePflanzenErnten.setOnAction(e -> {
            leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonStatusErnten(PflanzenStatus.eReif));
        });

        Button pflanzenEinerArtErnten = new Button("Pflanzen einer Art ernten");
        pflanzenEinerArtErnten.setOnAction(e -> {
            List<String> choices = new ArrayList<>();
            choices.add("Tomaten");
            choices.add("Gurken");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Gurken", choices);
            dialog.setTitle("Pflanzenart-Auswahl");
            dialog.setContentText("Bitte wählen Sie eine zu erntende Pflanzenart");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(pArt -> {
                switch (pArt) {
                    case "Tomaten":
                        leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonArtErnten(PflanzenArt.eTomate));
                        break;
                    case "Gurken":
                        leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonArtErnten(PflanzenArt.eGurke));
                        break;
                }
            });
        });

        Button pflanzenEinerArtScannen = new Button("Pflanzen einer Art scannen");
        pflanzenEinerArtScannen.setOnAction(e -> {
            List<String> choices = new ArrayList<>();
            choices.add("Tomaten");
            choices.add("Gurken");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Gurken", choices);
            dialog.setTitle("Pflanzenart-Auswahl");
            dialog.setContentText("Bitte wählen Sie eine zu scannende Pflanzenart");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(pArt -> {
                switch (pArt) {
                    case "Tomaten":
                        leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonArtScannen(PflanzenArt.eTomate));
                        break;
                    case "Gurken":
                        leitSystem.auftragHinzufuegen(auftragsgenerator.pflanzenVonArtScannen(PflanzenArt.eGurke));
                        break;
                }
            });
        });


        GridPane aktionsgrid = new GridPane();
        aktionsgrid.setHgap(10);
        aktionsgrid.setVgap(10);
        aktionsgrid.setPadding(new Insets(25, 25, 25, 25));
        aktionsgrid.add(pflanzenAuswahl, 0, 0);
        aktionsgrid.add(pflanzeHinzufuegen, 0, 1);
        aktionsgrid.add(pflanzenEinerArtErnten, 1, 1);
        aktionsgrid.add(pflanzenEinerArtScannen, 0, 2);
        aktionsgrid.add(reifePflanzenErnten, 1, 2);

        grid.add(aktionsgrid, 3, 0, 1, 2);
        Separator sep = new Separator();
        grid.add(sep, 3, 2);
        grid.add(simulationsGrid, 3, 3, 1, 2);
        grid.add(eigenschaftsgrid, 3, 5);









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