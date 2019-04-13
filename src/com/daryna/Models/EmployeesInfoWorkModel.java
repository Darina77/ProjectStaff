package com.daryna.Models;

import com.daryna.Models.Data.EmployeeInfoWork;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeesInfoWorkModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<EmployeeInfoWork> data;

    public EmployeesInfoWorkModel(List<EmployeeInfoWork> data)
    {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Description";
            case 1:
                return "Project name";
            case 2:
                return "Stage";
            case 3:
                return "Start date";
            case 4:
                return "End date";
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
        EmployeeInfoWork current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getDescription();
            case 1:
                return current.getProjectName();
            case 2:
                return current.getId();
            case 3:
                return current.getStartDate();
            case 4:
                return current.getEndDate();

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
