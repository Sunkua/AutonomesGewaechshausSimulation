package gewaechshaus.fxGUI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Hauptgrid extends GridPane {
	
	private Aktionsgrid aktionsgrid;
	private Button ernten;
    private Button scannen;
    private Button pflanzeHinzufuegen;
    private Button pflanzeEntfernen;
	
	public Hauptgrid(Aktionsgrid aktionsgrid) {
		super();
		this.aktionsgrid = aktionsgrid;
		
		// Design des Grids
		this.setAlignment(Pos.CENTER_LEFT);
        this.setHgap(10);
        this.setVgap(10);
		
	    // Komponenten laden
	    ernten = new Button("Ernten");
	    scannen = new Button("Scannen");
	    pflanzeHinzufuegen = new Button("Pflanze hinzufügen");
	    pflanzeEntfernen = new Button("Pflanze Entfernen");
	
	    // Event-Listener setzen
	    ernten.setOnAction(e -> aktionsgrid.oeffneErntenGrid());
	    scannen.setOnAction(e -> aktionsgrid.oeffneScannenGrid());
	    pflanzeEntfernen.setOnAction(e -> aktionsgrid.pflanzeEntf());
	    pflanzeHinzufuegen.setOnAction(e -> aktionsgrid.pflanzeHinzu());
	
	    // Komponenten ins Grid einfügen
	    this.add(ernten, 0, 0);
	    this.add(scannen, 1, 0);
	    this.add(pflanzeEntfernen, 0, 1);
	    this.add(pflanzeHinzufuegen, 1, 1);
	}
}
