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
import java.util.Random;
import java.util.logging.Level;

/**
 * Einstiegspunkt, Verwaltung und Hauptklasse der GUI und des Gewächshauses
 * selbst
 */
public class FXGUI extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialisiert die GUI und das gesamte Gewächshaus, inkl. Leitsystem,
     * Verwaltung, Robotern und Stationen
     */
    @Override
    public void start(Stage stage) {
        // Breite und Höhe werden als fester Wert angenommen, der der Feldanzahl
        // entspricht. Die richtigen Maße müssten
        // relativ zur Feldanzahl bzw. Breite oder Höhe berechnet werden
        Gitter gitter = new Gitter(24, 24f, 24, 24);

        Pflanzenverwaltung pVerwaltung = new Pflanzenverwaltung(gitter);

        Uhr uhr = new Uhr(300);
        Roboterleitsystem leitSystem = new Roboterleitsystem(gitter, uhr);
        Auftragsgenerator auftragsgenerator = new Auftragsgenerator(pVerwaltung, leitSystem, uhr);
        Position abladestelle = new Position(23f, 23f);
        Position abladestelle2 = new Position(0f, 0f);

        gitter.toKarthesisch(abladestelle);
        gitter.toKarthesisch(abladestelle2);
        Abladestation abladestation = new Abladestation(abladestelle);
        Abladestation abladestation2 = new Abladestation(abladestelle2);

        Position ladestelle = new Position(0f, 23f);
        Position ladestelle2 = new Position(23f, 0f);
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
        for (int i = 0; i < 10; i++) {
            leitSystem.roboterHinzufuegen(pVerwaltung);
        }

        FXEigenschaftenGUI eigenschaftenGitter = new FXEigenschaftenGUI(leitSystem);
        for (Roboter r : leitSystem.getRoboter()) {
            r.addObserver(eigenschaftenGitter);
        }
        abladestation.addObserver(eigenschaftenGitter);
        abladestation2.addObserver(eigenschaftenGitter);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));


        Scene scene = new Scene(grid, 400, 400, Color.BLACK);

        Text scenetitle = new Text("Gewächshausverwaltungssoftware");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        grid.add(scenetitle, 0, 0, 1, 1);


        FXGewaechshausCanvas canvas = new FXGewaechshausCanvas(
                (int) Math.round(scene.getWidth() / Konstanten.skalierungsfaktor), gitter, Konstanten.canvasDimensionX,
                Konstanten.canvasDimensionY, pVerwaltung, leitSystem);
        grid.add(canvas, 0, 4, 1, 2);

        // Stage building
        stage.setScene(scene);
        stage.setTitle("Gewächshaus-Roboter");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        // set Stage boundaries to visible bounds of the main screen
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
        Button loggingAnAus = new Button("Logging ausschalten");
        loggingAnAus.setOnAction(e -> {
            Konstanten.loggingAn = !Konstanten.loggingAn;
            if (Konstanten.loggingAn) {
                loggingAnAus.setText("Logging ausschalten");
            } else {
                loggingAnAus.setText("Logging anschalten");
            }
        });
        NummerFeld simulationsPeriode = new NummerFeld();
        simulationsPeriode.setText("1");

        timerStart.setOnAction(e -> uhr.startTimer());
        timerStop.setOnAction(e -> uhr.stopTimer());
        timerPeriodeAktualisieren.setOnAction(e -> {
            if (!simulationsPeriode.getText().equals("")) {
                int schrittZeit = Integer.parseInt(simulationsPeriode.getText());
                if (schrittZeit > 0)
                    uhr.setSchrittZeit(schrittZeit);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warnung");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Bitte geben Sie eine positive Zahl für den Takt ein");
                alert.showAndWait();
            }
        });

        simulationsGrid.add(timerStart, 0, 0);
        simulationsGrid.add(timerStop, 1, 0);
        simulationsGrid.add(timerPeriodeAktualisieren, 0, 2);
        simulationsGrid.add(simulationsPeriode, 1, 2);
        simulationsGrid.add(simulationsSchritt, 0, 3);
        simulationsGrid.add(loggingAnAus, 1, 3);

        ObservableList<String> options = FXCollections.observableArrayList("Gurken", "Tomaten");

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
                        Logging.log(this.getClass().getName(), Level.WARNING,
                                "ungültiger Wert aus Combobox für das Hinzufügen ausgewählt");
                        break;
                }
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Achtung, es konnte keine Pflanze hinzugefügt werden, da das Gewächshaus über keine weiteren leeren Beete verfügt");
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
        /**
         *  Scannen ist nicht implementiert, könnte aber nach erfolgreicher Implementierung wie nachfolgend hinzugefügt
         *  werden
         *  aktionsgrid.add(pflanzenEinerArtScannen, 0, 2);
         */

        aktionsgrid.add(reifePflanzenErnten, 1, 2);

        grid.add(aktionsgrid, 3, 0, 1, 2);
        Separator sep = new Separator();
        grid.add(sep, 3, 2);
        grid.add(simulationsGrid, 3, 3, 1, 2);
        grid.add(eigenschaftenGitter, 3, 5);
        Random randomGenerator = new Random();
        // Pflanzen hinzufügen
        for (int i = 0; i < 1000; i++) {
            try {
                int randomInt = randomGenerator.nextInt(2);
                if (randomInt % 2 == 0) {
                    pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eGurke);
                } else {
                    pVerwaltung.pflanzeHinzufuegen(PflanzenArt.eTomate);
                }

            } catch (Exception e) {
                Logging.log(this.getClass().getName(), Level.WARNING, "Keine freie Pflanzenposition gefunden");
            }
        }
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        canvas.paint();
        stage.show();
    }
}