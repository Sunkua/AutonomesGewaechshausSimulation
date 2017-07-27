package gewaechshaus.fxGUI;

import gewaechshaus.logic.Abladestation;
import gewaechshaus.logic.Roboter;
import gewaechshaus.logic.Roboterleitsystem;
import javafx.geometry.Pos;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.GridPane;

import java.util.Observable;
import java.util.Observer;

public class FXEigenschaftenGUI extends GridPane implements Observer {

	TabPane tabPane;
	FXRoboterTab roboterTab;
	FXAblageTab ablageTab;

	public FXEigenschaftenGUI(Roboterleitsystem r) {
		// TODO Auto-generated constructor stub
		super();
		this.setAlignment(Pos.CENTER_LEFT);
		this.setHgap(10);
		this.setVgap(10);

		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		roboterTab = new FXRoboterTab(r);
		roboterTab.setText("Roboter");
		tabPane.getTabs().add(roboterTab);

		ablageTab = new FXAblageTab(r);
		ablageTab.setText("Ablagestationen");
		tabPane.getTabs().add(ablageTab);

		this.add(tabPane, 0, 1);
	}

	@Override
	public void update(Observable observable, Object o) {
		if (observable instanceof Roboter) {
			roboterTab.UpdateData((Roboter) observable);
		}
		if (observable instanceof Abladestation) {
			ablageTab.UpdateData((Abladestation) observable);
		}
	}
}
