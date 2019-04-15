package com.kharko.controllers;

import com.kharko.types.Viddil;
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
    ComboBox<Viddil> viddil;

    @FXML
    Button pay;

    @FXML
    Button payHead;

    @FXML
    Button payViddil;

    @FXML
    Button list;

    @FXML
    Button statistics;

    public void setWorkers(ObservableList<Worker> data) {
        worker.setItems(data);
    }

    public void setHeads(ObservableList<Head> data) { head.setItems(data); }

    public void setViddil(ObservableList<Viddil> data) { viddil.setItems(data); }

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

    @FXML
    protected void handlePayViddil() throws Exception {
        kek.getInstance().employeeDataAcessor.payViddil(viddil.getValue());
    }
}
