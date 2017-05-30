package gewaechshaus.fxGUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Created by sunku on 25.05.2017.
 */
public class Aktionsgrid extends GridPane {

    private Button ernten;
    private Button scannen;
    private Button pflanzeHinzufuegen;
    private Button pflanzeEntfernen;

    public Aktionsgrid() {
        super();
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        // Komponenten laden
        ernten = new Button("Ernten");
        scannen = new Button("Scannen");
        pflanzeHinzufuegen = new Button("Pflanze hinzufügen");
        pflanzeEntfernen = new Button("Pflanze Entfernen");

        // Event-Listener setzen
        ernten.setOnAction(e -> oeffneErntenGrid());
        scannen.setOnAction(e -> oeffneScannenGrid());
        pflanzeEntfernen.setOnAction(e -> pflanzeEntf());
        pflanzeHinzufuegen.setOnAction(e -> pflanzeHinzu());

        // Komponenten ins Grid einfügen
        this.add(ernten, 0, 0, 1, 1);
        this.add(scannen, 1, 0, 1, 1);
        this.add(pflanzeEntfernen, 0, 1, 1, 1);
        this.add(pflanzeHinzufuegen, 1, 1, 1, 1);
    }

    private void oeffneErntenGrid() {
        this.getChildren().removeAll();

        ernten.setVisible(false);
        scannen.setVisible(false);
        pflanzeEntfernen.setVisible(false);
        pflanzeHinzufuegen.setVisible(false);
        this.add(new ErntenGrid(), 0, 0);
    }

    private void pflanzeHinzu() {

    }

    private void pflanzeEntf() {

    }

    private void oeffneScannenGrid() {

    }


}
