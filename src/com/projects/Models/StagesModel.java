package com.projects.Models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

public class StagesModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Stages> data;
    private List<Stages> displayedData;
    private String currentProjectId;

    public StagesModel(List<Stages> data) {
        this.data = data;
        this.displayedData = data;
    }

    @Override
    public int getRowCount() {
        return displayedData.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Number";
            case 2:
                return "Start Date";
            case 3:
                return "Plan End Date";
            case 4:
                return "Real End Date";
            case 5:
                return "Price";
            case 6:
                return "Cost";
            case 7:
                return "Id project";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Double.class;
            case 6:
                return Double.class;
            case 7:
                return Integer.class;
        }
        return String.class;

    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 0) return false;
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Stages current = displayedData.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getIdStage();
            case 1:
                return current.getNumStage();
            case 2:
                return current.getStartDate();
            case 3:
                return current.getPlanEndDate();
            case 4:
                return current.getRealEndDate();
            case 5:
                return current.getPrice();
            case 6:
                return current.getCost();
            case 7:
                return current.getIdProject();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Stages current = displayedData.get(rowIndex);
        switch (columnIndex) {
            case 0:
                current.setIdStage((int) aValue);
                break;
            case 1:
                current.setNumStage((int) aValue);
                break;
            case 2:
                current.setStartDate((String) aValue);
                break;
            case 3:
                current.setPlanEndDate((String) aValue);
                break;
            case 4:
                current.setRealEndDate((String) aValue);
                break;
            case 5:
                current.setPrice((double) aValue);
                break;
            case 6:
                current.setCost((double) aValue);
                break;
            case 7:
                current.setIdProject((int) aValue);
                break;

        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public boolean hasData(Stages stages) {
        return data.contains(stages);
    }

    public void addData(Stages stages) {
        int id = data.get(data.size()-1).getIdStage() + 1;
        stages.setIdStage(id);
        data.add(stages);
        update(currentProjectId);
    }

    public void removeData(int idStage) {
        Stages temp = new Stages(idStage);
        if (!hasData(temp)) throw new NoSuchElementException("No such stage");
        data.remove(temp);
        update(currentProjectId);
    }

    public List<Stages> getData() {
        return this.data;
    }

    public void update(String idS) {
        currentProjectId = idS;
        if (idS == null || idS == "") {
            displayedData = data;
        } else {
            displayedData = new ArrayList<Stages>();
            int id = Integer.parseInt(idS);
            for (Stages s : data)
            {
                if (s.getIdProject() == id)
                {
                    displayedData.add(s);
                }
            }
        }
    }
}
