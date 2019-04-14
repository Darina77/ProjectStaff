package com.kharko.helpers;

import javafx.collections.ObservableList;
import com.kharko.Utils.kek;
import com.kharko.dao.PersonDataAccessor;
import com.kharko.types.Position;
import com.kharko.types.Viddil;

public class PersonDataHelper {

    private PersonDataAccessor dataAccessor;

    public PersonDataHelper(kek val) throws Exception{
        this.setAccessor(val);
    }

    private void setAccessor(kek kek) throws Exception{
        this.dataAccessor = kek.dataAccessor;
    }

    public void deletePosition(String name) throws Exception{
        dataAccessor.deletePerson(name);
    }

    public ObservableList<Position> getPosition() throws Exception{
        return dataAccessor.getPersonList();
    }

    public ObservableList<Viddil> getViddil() throws Exception{
        return dataAccessor.getViddilList();
    }

    public void addPosition(Position position) throws Exception{
        dataAccessor.addPerson(position);
    }
}
