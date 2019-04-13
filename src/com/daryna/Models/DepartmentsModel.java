package com.daryna.Models;

import com.daryna.Models.Data.Department;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class DepartmentsModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Department> data;

    public DepartmentsModel(List<Department> data)
    {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Name";
            case 1:
                return "Phone number";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        if (columnIndex == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Department current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getName();
            case 1:
                return current.getPhoneNumber();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
         switch (columnIndex) {
             case 0:
                 data.get(rowIndex).setName((String) aValue);
                 break;
             case 1:
                 data.get(rowIndex).setPhoneNumber((String) aValue);
                 break;
         }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public boolean hasData(Department dep)
    {
        return data.contains(dep);
    }

    public void addData(Department dep) throws CloneNotSupportedException {
        if (hasData(dep)) throw new CloneNotSupportedException("Department exists");
        data.add(dep);
    }

    public void removeData(String depName) throws NoSuchElementException
    {
        Department temp = new Department(depName, "");
        if (!hasData(temp)) throw new NoSuchElementException("No such department");
        data.remove(temp);
    }

    public List<Department> getData()
    {
        return this.data;
    }
}

