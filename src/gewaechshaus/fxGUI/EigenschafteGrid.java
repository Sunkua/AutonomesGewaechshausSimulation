package gewaechshaus.fxGUI;


import gewaechshaus.logic.Roboter;
import gewaechshaus.logic.Roboterleitsystem;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class EigenschafteGrid extends GridPane implements Observer {

	TabPane tabPane;
	RoboterTab roboterTab;

	public EigenschafteGrid(Roboterleitsystem r) {
		// TODO Auto-generated constructor stub
		super();
		this.setAlignment(Pos.CENTER_LEFT);
        this.setHgap(10);
        this.setVgap(10);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        roboterTab = new RoboterTab(r);
        roboterTab.setText("Roboter");
        tabPane.getTabs().add(roboterTab);
        this.add(tabPane,0,1);
	}

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof Roboter) {
        	roboterTab.UpdateData((Roboter) observable);
        }
    }
}
