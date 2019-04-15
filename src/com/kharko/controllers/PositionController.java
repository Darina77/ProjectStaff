package com.kharko.controllers;

import com.daryna.Models.Data.Employee;
import com.kharko.types.Salary;
import com.kharko.types.Worker;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import com.kharko.Utils.kek;
import com.kharko.helpers.PersonDataHelper;
import com.kharko.types.Position;
import com.kharko.types.Viddil;

public class PositionController  {


    @FXML
    private ComboBox<Position> target;

    @FXML
    private ComboBox<Viddil> viddil;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private TextField posName;

    @FXML
    private TextField salary;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<Worker> employee;

    @FXML
    TableView<Position> table;

    public void setData(ObservableList<Position> data) {

        TableColumn firstNameCol = new TableColumn("Назва");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameCol.setPrefWidth(96.0);
        TableColumn lastNameCol = new TableColumn("Зарплата");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        lastNameCol.setPrefWidth(103.0);
        table.getColumns().setAll(firstNameCol, lastNameCol);
        table.setItems(data);
        target.setItems(data);
    }

    public void setViddilDate(ObservableList<Viddil> data) { viddil.setItems(data); }

    public void setEmployees(ObservableList<Worker> data) { employee.setItems(data); }

    @FXML
    protected void handleDeleteButtonAction(ActionEvent event) throws Exception {
        PersonDataHelper helper = new PersonDataHelper(kek.getInstance());
        Window owner = deleteButton.getScene().getWindow();
        if(target.getValue() != null) {
            helper.deletePosition(target.getValue().getId());
            setData(helper.getPosition());
        }

    }

    @FXML
    protected void handleAddButtonAction(ActionEvent event) throws Exception {
        String salaryValue = salary.getText();
        String posNameValue = posName.getText();
        String startDateValue = startDate.getValue().toString();
        String endDateValue  = endDate.getValue().toString();

        PersonDataHelper helper = new PersonDataHelper(kek.getInstance());
        Window owner = addButton.getScene().getWindow();
        if(salaryValue != null&&posNameValue != null&&viddil.getValue().getidDep()!=null) {
            Position a = new Position(salaryValue,viddil.getValue().getidDep(),posNameValue,startDateValue,endDateValue,employee.getValue().getid());
            helper.addPosition(a);
            setData(helper.getPosition());
        }

    }

    @FXML
    protected void handleStatisticsButtonAction(ActionEvent event) throws Exception {
        kek.getInstance().setStatistics();
    }

    @FXML
    protected void handlePaymentsButtonAction(ActionEvent event) throws Exception {
        kek.getInstance().setPay();
    }


}