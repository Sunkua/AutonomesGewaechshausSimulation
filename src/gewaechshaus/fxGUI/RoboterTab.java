package gewaechshaus.fxGUI;

import gewaechshaus.logic.Roboter;
import gewaechshaus.logic.Roboterleitsystem;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoboterTab extends Tab {
	
   public class RobotRecord {
        private SimpleStringProperty fieldName;
        private SimpleStringProperty fieldPosition;
        private SimpleStringProperty fieldAuftrag;
        private SimpleDoubleProperty fieldLadung;

        RobotRecord(String name, String position, String Aufrag, double Ladung) {
            this.fieldName = new SimpleStringProperty(name);
            this.fieldPosition = new SimpleStringProperty(position);
            this.fieldAuftrag = new SimpleStringProperty(Aufrag);
            this.fieldLadung = new SimpleDoubleProperty(Ladung);
        }

        public String getFieldName() {
            return fieldName.get();
        }

        public void setFieldName(String fieldName) {
            this.fieldName.set(fieldName);
        }

        public String getFieldPosition() {
            return fieldPosition.get();
        }

        public void setFieldPosition(String fieldPosition) {
            this.fieldPosition.set(fieldPosition);
        }

        public String getFieldAuftrag() {
            return fieldAuftrag.get();
        }

        public void setFieldAuftrag(String fieldAuftrag) {
            this.fieldAuftrag.set(fieldAuftrag);
        }

        public double getFieldLadung() {
            return fieldLadung.get();
        }

        public void setFieldLadung(double fieldLadung) {
            this.fieldLadung.set(fieldLadung);
        }
    }

    Roboterleitsystem roboterleitsystem;
    TableView<RobotRecord> robotTable;
    private ObservableList<RobotRecord> robotList;
    
	public RoboterTab(Roboterleitsystem r) {
		super();
		roboterleitsystem = r;
		
        robotList = FXCollections.observableArrayList();
        for ( Roboter robot : roboterleitsystem.getRoboter()){
            RobotRecord rec = new RobotRecord(robot.getID().toString(), robot.getPosition().toString(), "n/a", robot.getLadestand());
            robotList.add(rec);
        }
        robotTable = new TableView<>();

        TableColumn columnName = new TableColumn("Name");
        columnName.setMinWidth(100);
        columnName.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldName"));

        TableColumn columnPosition = new TableColumn("Position");
        columnPosition.setMinWidth(100);
        columnPosition.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldPosition"));

        TableColumn columnAuftrag = new TableColumn("Aktueller Autrag");
        columnAuftrag.setMinWidth(100);
        columnAuftrag.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldAuftrag"));

        TableColumn columnAkku = new TableColumn("Akkulandung");
        columnAkku.setCellValueFactory(new PropertyValueFactory<RobotRecord, Double>("fieldLadung"));
        columnAkku.setMinWidth(100);

        robotTable.getColumns().addAll(columnName, columnPosition, columnAuftrag, columnAkku);
        robotTable.setItems(robotList);

        setContent(robotTable);
	}
	
	public void UpdateData(Roboter r){
		 for (RobotRecord rr : this.robotList) {
		     if (rr.getFieldName().equals(r.getID().toString())) {
		         //TODO Auftrag vom Leitsystem erfragen
		         rr.setFieldLadung(r.getLadestand());
		         rr.setFieldPosition(r.getPosition().toString());
		         robotTable.refresh();
		         break;
		     }
		 }
	} 
}
