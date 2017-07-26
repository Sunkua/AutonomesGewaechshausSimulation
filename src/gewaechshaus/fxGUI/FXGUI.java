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
import javafx.scene.layout.ColumnConstraints;
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
        // Breite und Höhe werden als fester Wert angenommen, der der Feldanzahl entspricht. Die richtigen Maße müssten
        // relativ zur Feldanzahl bzw. Breite oder Höhe berechnet werden
        Gitter gitter = new Gitter(12, 12f, 12, 12);

        Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung(gitter);

        Uhr uhr = new Uhr(300);
        Roboterleitsystem leitSystem = new Roboterleitsystem(gitter, uhr);
        Auftragsgenerator auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, uhr);
        Position abladestelle = new Position(11, 11f);
        Position abladestelle2 = new Position(0f, 0f);

        gitter.toKarthesisch(abladestelle);
        gitter.toKarthesisch(abladestelle2);
        Abladestation abladestation = new Abladestation(abladestelle);
        Abladestation abladestation2 = new Abladestation(abladestelle2);

        Position ladestelle = new Position(0f, 11f);
        Position ladestelle2 = new Position(11f, 0f);
        gitter.toKarthesisch(ladestelle);
        gitter.toKarthesisch(ladestelle2);
        Ladestation ladestation = new Ladestation(ladestelle);
        Ladestation ladestation2 = new Ladestation(ladestelle2);

        pVerwaltung.addObserver(leitSystem);
        pVerwaltung.addObserver(gitter);
        leitSystem.addObserver(gitter);
        uhr.addObserver(leitSystem);
        uhr.addObserver(pVerwaltung);


        leitSystem.abladestationHinzufuegen(abladestation);
        leitSystem.abladestationHinzufuegen(abladestation2);
        leitSystem.ladestationHinzufuegen(ladestation);
        leitSystem.ladestationHinzufuegen(ladestation2);

        // Roboter hinzufügen
        for (int i = 0; i < 5; i++) {
            leitSystem.roboterHinzufuegen(pVerwaltung);
        }

        FXEigenschaftenGUI eigenschaftsgrid = new FXEigenschaftenGUI(leitSystem);
        for (Roboter r : leitSystem.getRoboter()) {
            r.addObserver(eigenschaftsgrid);
        }
        abladestation.addObserver(eigenschaftsgrid);
        abladestation2.addObserver(eigenschaftsgrid);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));
        ColumnConstraints col0 = new ColumnConstraints();
        ColumnConstraints col1 = new ColumnConstraints();
        col0.setPercentWidth(60);
        col1.setPercentWidth(40);
        grid.getColumnConstraints().addAll(col0, col1);


        // Set width and height

        Scene scene = new Scene(grid, 400, 400, Color.BLACK);
        // Left Pane
        Text scenetitle = new Text("Gewächshausverwaltungssoftware");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));


        grid.add(scenetitle, 0, 0, 1, 1);


        //   Aktionsgrid interaktionsGrid = new Aktionsgrid();


        // Canvas-Building, Event-Listeners redraw on rescale
        FXGewaechshausCanvas canvas = new FXGewaechshausCanvas((int) Math.round(scene.getWidth() / Konstanten.skalierungsfaktor), gitter, Konstanten.canvasDimensionX, Konstanten.canvasDimensionY, pVerwaltung, leitSystem);
        grid.add(canvas, 0, 2, 1, 2);

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

        ComboBox<String> pflanzenAuswahl = new ComboBox<String>(options);
        pflanzenAuswahl.setValue("Gurken");

        // Pflanze hinzufügen
        Button pflanzeHinzufuegen = new Button("Pflanze Hinzufügen");
        pflanzeHinzufuegen.setOnAction(e -> {
            String comboboxWert = pflanzenAuswahl.getValue();
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
        // Scannen ist nicht implementiert
        //  aktionsgrid.add(pflanzenEinerArtScannen, 0, 2);
        aktionsgrid.add(reifePflanzenErnten, 1, 2);

        grid.add(aktionsgrid, 3, 0, 1, 2);
        Separator sep = new Separator();
        grid.add(sep, 3, 2);
        grid.add(simulationsGrid, 3, 3, 1, 2);
        grid.add(eigenschaftsgrid, 3, 5);


        // Pflanzen hinzufügen
        for (int i = 0; i < 1000; i++) {
            try {
                //       pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
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