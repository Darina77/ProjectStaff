package com.daryna.Models;

import com.daryna.Models.Data.DepartmentInfo;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentInfoModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<DepartmentInfo> data;

    public DepartmentInfoModel(List<DepartmentInfo> data)
    {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Position";
            case 1:
                return "Salary";
            case 2:
                return "Id";
            case 3:
                return "Surname";

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
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DepartmentInfo current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getPosition();
            case 1:
                return current.getSalary();
            case 2:
                return current.getId();
            case 3:
                return current.getSurname();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
