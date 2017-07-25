package gewaechshaus.fxGUI;

import gewaechshaus.logic.Roboter;
import gewaechshaus.logic.Roboterleitsystem;
import gewaechshaus.logic.Unterauftrag;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXRoboterTab extends Tab {

    Roboterleitsystem roboterleitsystem;
    TableView<RobotRecord> robotTable;
    private ObservableList<RobotRecord> robotList;

    public FXRoboterTab(Roboterleitsystem r) {
        super();
        roboterleitsystem = r;

        robotList = FXCollections.observableArrayList();
        for (Roboter robot : roboterleitsystem.getRoboter()) {

            RobotRecord rec = new RobotRecord(robot.getID().toString(), robot.getPosition().toString(), unterauftragAlsString(robot.getUnterauftrag()), robot.getLadestand(), robot.getFüllstandString());
            robotList.add(rec);
        }
        robotTable = new TableView<>();

        TableColumn<RobotRecord, String> columnName = new TableColumn<RobotRecord, String>("Name");
        columnName.setMinWidth(200);
        columnName.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldName"));

        TableColumn<RobotRecord, String> columnPosition = new TableColumn<RobotRecord, String>("Position");
        columnPosition.setMinWidth(100);
        columnPosition.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldPosition"));

        TableColumn<RobotRecord, String> columnAuftrag = new TableColumn<RobotRecord, String>("Aktueller Autrag");
        columnAuftrag.setMinWidth(100);
        columnAuftrag.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldAuftrag"));

        TableColumn<RobotRecord, String> columnAkku = new TableColumn<RobotRecord, String>("Akkulandung");
        columnAkku.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldLadung"));
        columnAkku.setMinWidth(80);

        TableColumn<RobotRecord, String> columnFüllstand = new TableColumn<RobotRecord, String>("Füllstand");
        columnFüllstand.setCellValueFactory(new PropertyValueFactory<RobotRecord, String>("fieldFüllstand"));
        columnFüllstand.setMinWidth(100);

        robotTable.getColumns().addAll(columnName, columnPosition, columnAuftrag, columnAkku, columnFüllstand);
        robotTable.setItems(robotList);

        setContent(robotTable);
    }

    public void UpdateData(Roboter r) {
        for (RobotRecord rr : this.robotList) {
            if (rr.getFieldName().equals(r.getID().toString())) {
                //TODO Auftrag vom Leitsystem erfragen
                rr.setFieldLadung(r.getLadestand());
                rr.setFieldPosition(r.getPosition().toString());
                rr.setFieldFüllstand(r.getFüllstandString());
                rr.setFieldAuftrag(unterauftragAlsString(r.getUnterauftrag()));
                robotTable.refresh();
                break;
            }
        }
    }

    private String unterauftragAlsString(Unterauftrag uAuftrag) {
        if (uAuftrag == null) {
            return "Kein aktueller Auftrag";
        } else {
            return uAuftrag.toString();
        }
    }

    public class RobotRecord {
        private SimpleStringProperty fieldName;
        private SimpleStringProperty fieldPosition;
        private SimpleStringProperty fieldAuftrag;
        private SimpleStringProperty fieldLadung;
        private SimpleStringProperty fieldFüllstand;

        RobotRecord(String name, String position, String Aufrag, double Ladung, String Füllstand) {
            this.fieldName = new SimpleStringProperty(name);
            this.fieldPosition = new SimpleStringProperty(position);
            this.fieldAuftrag = new SimpleStringProperty(Aufrag);

            Double l = new Double(Ladung);
            double tmp = Math.round(l * 100);
            l = tmp / 100;
            this.fieldLadung = new SimpleStringProperty(l.toString() + " %");

            this.fieldFüllstand = new SimpleStringProperty(Füllstand);
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

        public String getFieldLadung() {
            return fieldLadung.get();
        }

        public void setFieldLadung(double fieldLadung) {
            Double l = new Double(fieldLadung);
            double tmp = Math.round(l * 100);
            l = tmp / 100;
            this.fieldLadung.set(l.toString() + " %");
        }

        public String getFieldFüllstand() {
            return fieldFüllstand.get();
        }

        public void setFieldFüllstand(String fieldFüllstand) {
            this.fieldFüllstand.set(fieldFüllstand);
        }
    }
}
