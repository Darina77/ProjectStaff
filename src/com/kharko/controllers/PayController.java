package com.kharko.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import com.kharko.Utils.kek;
import com.kharko.types.Head;
import com.kharko.types.Worker;

public class PayController {

    @FXML
    ComboBox<Worker> worker;

    @FXML
    ComboBox<Head> head;

    @FXML
    Button pay;

    @FXML
    Button payHead;

    @FXML
    Button list;

    @FXML
    Button statistics;

    public void setWorkers(ObservableList<Worker> data) {
        worker.setItems(data);
    }

    public void setHeads(ObservableList<Head> data) { head.setItems(data); }

    @FXML
    protected void handlePosada() throws Exception{
        kek.getInstance().setPosad();
    }

    @FXML
    protected void handleStatistics() throws Exception{
        kek.getInstance().setStatistics();
    }

    @FXML
    protected void handlePay() throws Exception {
        kek.getInstance().employeeDataAcessor.pay(worker.getValue());
    }

    @FXML
    protected void handlePayHead() throws Exception {
        kek.getInstance().employeeDataAcessor.payHead(head.getValue());
    }

}
