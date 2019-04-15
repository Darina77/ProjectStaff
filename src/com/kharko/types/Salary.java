package com.kharko.types;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Salary {

    private final StringProperty id = new SimpleStringProperty(this, "id");
    public StringProperty idProperty() {
        return id ;
    }
    public final String getid() {
        return idProperty().get();
    }
    public final void setid(String idDep) {
        idProperty().set(idDep);
    }

    private final StringProperty amount = new SimpleStringProperty(this, "amount");
    public StringProperty amount() {
        return amount ;
    }
    public final String getAmount() {
        return amount().get();
    }
    public final void setamount(String amount) {
        amount().set(amount);
    }

    private final StringProperty name = new SimpleStringProperty(this, "name");
    public StringProperty name() {
        return name ;
    }
    public final String getName() {
        return name().get();
    }
    public final void setName(String month) {
        name().set(month);
    }

    private final StringProperty month = new SimpleStringProperty(this, "month");
    public StringProperty month() {
        return month ;
    }
    public final String getMonth() {
        return month().get();
    }
    public final void setmonth(String month) {
        month().set(month);
    }

    private final StringProperty idEmp = new SimpleStringProperty(this, "idEmp");
    public StringProperty idEmp() {
        return idEmp ;
    }
    public final String getidEmp() {
        return idEmp().get();
    }
    public final void setidEmp(String idEmp) {
        idEmp().set(idEmp);
    }



    public Salary() {

    }

    public Salary(String id,String amount, String month, String idEmp, String name) {
        setid(id);
        setamount(amount);
        setmonth(month);
        setidEmp(idEmp);
        setName(name);
    }
}
