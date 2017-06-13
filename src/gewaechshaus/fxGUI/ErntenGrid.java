package gewaechshaus.fxGUI;

import gewaechshaus.logic.Auftrag;
import gewaechshaus.logic.PflanzenArt;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ErntenGrid extends GridPane {

    public ErntenGrid() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setHgap(10);
        this.setVgap(10);

        // Komponenten laden
        Button nachArtErnten = new Button("Eine Art ernten");
        Button nachReifegradErnten = new Button("Nach Reifegrad ernten");
        Button einePflanzeErnten = new Button("Eine einzige Pflanze ernten");
        Button alleErnten = new Button("Alle ernten");
        
        alleErnten.setOnAction(e -> allesErnten());

        // Event-Listener setzen

        // Komponenten ins Grid einfügen
        this.add(nachArtErnten, 0, 0);
        this.add(nachReifegradErnten, 1, 0);
        this.add(einePflanzeErnten, 0, 1);
        this.add(alleErnten, 1, 1);
    }
    
    private void allesErnten() {
    	// TODO: Nick - Wie läuft das mit dem Generator? Muss ich für jeden einen eigenen Generator anlegen?
    	//Auftrag allesErnten = auftragsgenerator.pflanzenVonArtErnten(PflanzenArt.eGurke);
        //leitSystem.auftragHinzufuegen(gurkenErnten);
    }
}
