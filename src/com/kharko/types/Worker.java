package com.kharko.types;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Worker {

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

    /*private final StringProperty idPos = new SimpleStringProperty(this, "idPos");*/


    /*public StringProperty idPos() {
        return idPos ;
    }
    public final String getidPos() {
        return idPos().get();
    }
    public final void setidPos(String name) {
        idPos().set(name);
    }*/

    @Override
    public String toString() {
        return name.get();
    }

    public Worker() {

    }

    public Worker(String id,String name) {
        setid(id);
        setName(name);
        //setidPos(idPos);
    }

}
