package com.kharko.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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

    public void setData(ObservableList<Position> data) {
        target.setItems(data);
    }

    public void setViddilDate(ObservableList<Viddil> data) { viddil.setItems(data); }

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
            Position a = new Position(salaryValue,viddil.getValue().getidDep(),posNameValue,startDateValue,endDateValue);
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