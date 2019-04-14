package com.kharko.types;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Head {

    private final StringProperty id = new SimpleStringProperty(this, "idDep");
    public StringProperty idProperty() {
        return id ;
    }
    public final String getid() {
        return idProperty().get();
    }
    public final void setid(String idDep) {
        idProperty().set(idDep);
    }

    private final StringProperty name = new SimpleStringProperty(this, "name");
    public StringProperty idName() {
        return name ;
    }
    public final String getName() {
        return idName().get();
    }
    public final void setName(String name) {
        idName().set(name);
    }

    private final StringProperty idEmp = new SimpleStringProperty(this, "idEmp");
    public StringProperty idEmp() {
        return idEmp ;
    }
    public final String getidEmp() {
        return idEmp().get();
    }
    public final void setidEmp(String name) {
        idEmp().set(name);
    }

    private final StringProperty salary = new SimpleStringProperty(this, "salary");
    public StringProperty salaryProperty() {
        return salary ;
    }
    public final String getSalary() {
        return salaryProperty().get();
    }
    public final void setSalary(String salary) {
        salaryProperty().set(salary);
    }


    @Override
    public String toString() {
        return getName();
    }

    public Head() {

    }

    public Head(String id,String name,String salary, String idEmp) {
        setid(id);
        setName(name);
        setidEmp(idEmp);
        setSalary(salary);
    }
}
