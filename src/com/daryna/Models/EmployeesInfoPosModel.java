package com.daryna.Models;

import com.daryna.Models.Data.EmployeeInfoPos;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeesInfoPosModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<EmployeeInfoPos> data;

    public EmployeesInfoPosModel(List<EmployeeInfoPos> data)
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
                return "Position";
            case 1:
                return "Department";
            case 2:
                return "Start date";
            case 3:
                return "End date";
            case 4:
                return "Salary";
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
        EmployeeInfoPos current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getPosition();
            case 1:
                return current.getDepName();
            case 2:
                return current.getStartDate();
            case 3:
                return current.getEndDate();
            case 4:
                return current.getSalary();
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
