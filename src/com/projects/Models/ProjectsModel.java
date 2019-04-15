package com.projects.Models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.*;

public class ProjectsModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Project> data;

    public ProjectsModel(List<Project> data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Id";
            case 1:
                return "Name";
            case 2:
                return "Client";
            case 3:
                return "Start Date";
            case 4:
                return "Plan End Date";
            case 5:
                return "Real End Date";
            case 6:
                return "Price";
            case 7:
                return "Cost";
            case 8:
                return "Rating";
            case 9:
                return "Id department";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {

        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            case 6:
                return Double.class;
            case 7:
                return Double.class;
            case 8:
                return String.class;
            case 9:
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
        Project current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getId();
            case 1:
                return current.getName();
            case 2:
                return current.getClient();
            case 3:
                return current.getStartDate();
            case 4:
                return current.getPlanEndDate();
            case 5:
                return current.getRealEndDate();
            case 6:
                return current.getPrice();
            case 7:
                return current.getCost();
            case 8:
                return current.getRating();
            case 9:
                return current.getDepartmentId();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Project current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                current.setId((int) aValue);
                break;
            case 1:
                current.setName((String) aValue);
                break;
            case 2:
                current.setClient((String) aValue);
                break;
            case 3:
                current.setStartDate((String) aValue);
                break;
            case 4:
                current.setPlanEndDate((String) aValue);
                break;
            case 5:
                current.setRealEndDate((String) aValue);
                break;
            case 6:
                current.setPrice((double) aValue);
                break;
            case 7:
                current.setCost((double) aValue);
                break;
            case 8:
                current.setRating((String) aValue);
                break;
            case 9:
                current.setDepartmentId((int) aValue);
                break;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

    public boolean hasData(Project stages) {
        return data.contains(stages);
    }

    public void addData(Project project) {
        int id = data.get(data.size()-1).getId() + 1;
        project.setId(id);
        data.add(project);
    }

    public void removeData(int idStage) {
        Project temp = new Project(idStage);
        if (!hasData(temp)) throw new NoSuchElementException("No such project");
        data.remove(temp);
    }

    public List<Project> getData() {
        return this.data;
    }
}
