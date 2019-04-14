package com.kharko.types;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Viddil {

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

    @Override
    public String toString() {
        return name.get();
    }

    public Viddil() {

    }

    public Viddil(String idDep,String name) {
        setidDep(idDep);
        setName(name);
    }


}
