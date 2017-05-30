package gewaechshaus.fxGUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Created by sunku on 25.05.2017.
 */
public class PflanzeHinzufuegen extends GridPane {

    public PflanzeHinzufuegen() {
        super();
        this.setAlignment(Pos.CENTER_RIGHT);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));

        // Komponenten laden
        Label titel = new Label();
        titel.setText("Pflanze Hinzufügen");
        Label lb_Spalte = new Label("Spalte");
        Label lb_Zeile = new Label("Zeile");
        Label lb_art = new Label("Art");
        Label lb_Reife = new Label("Reife");
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "",
                        "Option 2",
                        "Option 3"
                );
        final ComboBox comboBox = new ComboBox(options);

        ComboBox cb_Spalte = new ComboBox();


        Button nachArtErnten = new Button("Eine Art ernten");
        Button nachReifegradErnten = new Button("Nach Reifegrad ernten");
        Button einePflanzeErnten = new Button("Eine einzige Pflanze ernten");
        Button alleErnten = new Button("Alle ernten");

        // Event-Listener setzen

        // Komponenten ins Grid einfügen
        this.add(nachArtErnten, 0, 0, 1, 1);
        this.add(nachReifegradErnten, 1, 0, 1, 1);
        this.add(einePflanzeErnten, 0, 1, 1, 1);
        this.add(alleErnten, 1, 1, 1, 1);
    }


}
