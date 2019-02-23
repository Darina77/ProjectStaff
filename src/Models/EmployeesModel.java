package Models;

import Models.Data.Employee;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

public class EmployeesModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<Employee> data;

    public EmployeesModel(List<Employee> data)
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
                return "Id";
            case 1:
                return "Surname";
            case 2:
                return "Sex";
            case 3:
                return "Birthday";

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
        if (columnIndex == 0) return false;
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getId();
            case 1:
                return current.getSurname();
            case 2:
                return current.getSex();
            case 3:
                return current.getBirthday();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Employee current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                current.setId((int) aValue);
                break;
            case 1:
                current.setSurname((String) aValue);
                break;
            case 2:
                current.setSex(((String) aValue).charAt(0));
                break;
            case 3:
                try {
                    current.setBirthday((String) aValue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

    public boolean hasData(Employee employee) {
        return data.contains(employee);
    }

    public void addData(Employee employee) {
        int id = data.get(data.size()-1).getId() + 1;
        employee.setId(id);
        data.add(employee);
    }

    public void removeData(int idEmp) {
        Employee temp = new Employee(idEmp);
        if (!hasData(temp)) throw new NoSuchElementException("No such department");
        data.remove(temp);
    }

    public List<Employee> getData() {
        return this.data;
    }
}