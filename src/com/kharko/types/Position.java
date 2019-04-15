package com.kharko.types;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Position {
    private final StringProperty id = new SimpleStringProperty(this, "id");
    public StringProperty idProperty() {
        return id ;
    }
    public final String getId() {
        return idProperty().get();
    }
    public final void setId(String id) {
        idProperty().set(id);
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

    private final StringProperty idDep = new SimpleStringProperty(this, "idDep");
    public StringProperty idDepProperty() {
        return idDep ;
    }
    public final String getidDep() {
        return idDepProperty().get();
    }
    public final void setidDep(String idDep) {
        idDepProperty().set(idDep);
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

    private final StringProperty startDate = new SimpleStringProperty(this, "startDate");
    public StringProperty startDate() {
        return startDate ;
    }
    public final String getstartDate() {
        return startDate().get();
    }
    public final void setstartDate(String startDate) {
        startDate().set(startDate);
    }


    private final StringProperty endDate = new SimpleStringProperty(this, "endDate");
    public StringProperty endDate() {
        return endDate ;
    }
    public final String getendDate() {
        return endDate().get();
    }
    public final void setendDate(String endDate) {
        endDate().set(endDate);
    }

    private final StringProperty employeeId = new SimpleStringProperty(this, "employeeId");
    public StringProperty employeeId() {
        return employeeId ;
    }
    public final String getEmployeeId() {
        return employeeId().get();
    }
    public final void setEmployeeId(String endDate) {
        employeeId().set(endDate);
    }


    @Override
    public String toString() {
        return name.get();
    }

    public Position() {

    }

    public Position(String id, String salary, String idDep, String name, String employeeId) {
        setId(id);
        setSalary(salary);
        setidDep(idDep);
        setName(name);
        setEmployeeId(employeeId);
    }

    public Position(String salary, String idDep, String name, String startDate, String endDate, String employeeId) {
        setSalary(salary);
        setidDep(idDep);
        setName(name);
        setstartDate(startDate);
        setendDate(endDate);
        setEmployeeId(employeeId);
    }

}
