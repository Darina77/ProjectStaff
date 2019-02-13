package Models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
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
                current.setSex((char) aValue);
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
}