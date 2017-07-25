package gewaechshaus.fxGUI;

import gewaechshaus.logic.Abladestation;
import gewaechshaus.logic.Roboterleitsystem;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXAblageTab extends Tab {

    Roboterleitsystem roboterleitsystem;
    TableView<AblageRecord> abladeTable;
    private ObservableList<AblageRecord> abladeList;

    public FXAblageTab(Roboterleitsystem r) {
        super();
        roboterleitsystem = r;

        abladeList = FXCollections.observableArrayList();
        for (Abladestation a : roboterleitsystem.getAbladestationen()) {
            AblageRecord rec = new AblageRecord(a.getID().toString(), a.getPosition().toString(), String.format("%.2f", a.getFuellstand()) + " kg");
            abladeList.add(rec);
        }
        abladeTable = new TableView<>();

        TableColumn<AblageRecord, String> columnName = new TableColumn<AblageRecord, String>("Name");
        columnName.setMinWidth(200);
        columnName.setCellValueFactory(new PropertyValueFactory<AblageRecord, String>("fieldName"));

        TableColumn<AblageRecord, String> columnPosition = new TableColumn<AblageRecord, String>("Position");
        columnPosition.setMinWidth(100);
        columnPosition.setCellValueFactory(new PropertyValueFactory<AblageRecord, String>("fieldPosition"));


        TableColumn<AblageRecord, String> columnFüllstand = new TableColumn<AblageRecord, String>("Füllstand");
        columnFüllstand.setCellValueFactory(new PropertyValueFactory<AblageRecord, String>("fieldFüllstand"));
        columnFüllstand.setMinWidth(100);

        abladeTable.getColumns().addAll(columnName, columnPosition, columnFüllstand);
        abladeTable.setItems(abladeList);

        setContent(abladeTable);
    }

    public void UpdateData(Abladestation a) {
        for (AblageRecord ar : this.abladeList) {
            if (ar.getFieldName().equals(a.getID().toString())) {
                //TODO Auftrag vom Leitsystem erfragen
                ar.setFieldFüllstand(String.format("%.2f", a.getFuellstand()));
                abladeTable.refresh();
                break;
            }
        }
    }

    public class AblageRecord {
        private SimpleStringProperty fieldName;
        private SimpleStringProperty fieldPosition;
        private SimpleStringProperty fieldFüllstand;

        AblageRecord(String name, String position, String Füllstand) {
            this.fieldName = new SimpleStringProperty(name);
            this.fieldPosition = new SimpleStringProperty(position);
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

        public String getFieldFüllstand() {
            return fieldFüllstand.get();
        }

        public void setFieldFüllstand(String fieldFüllstand) {
            this.fieldFüllstand.set(fieldFüllstand);
        }
    }
}
