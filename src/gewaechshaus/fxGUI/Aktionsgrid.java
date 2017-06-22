package gewaechshaus.fxGUI;

import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

public class Aktionsgrid extends GridPane {

    public Aktionsgrid() {
        super();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        
        this.add(new Hauptgrid(this), 0, 1);
    }

    public void oeffneErntenGrid() {
    	schließeUnterGrid();
        this.add(new Separator(), 0, 2);
        this.add(new ErntenGrid(), 0, 3);
    }

    public void pflanzeHinzu() {
    	schließeUnterGrid();
    }

    public void pflanzeEntf() {
    	schließeUnterGrid();
    }

    public void oeffneScannenGrid() {
    	schließeUnterGrid();
    }

    private void schließeUnterGrid() {
    	if(this.getChildren().size() > 1) {
    		this.getChildren().remove(1, this.getChildren().size());
    	}
    }

}
