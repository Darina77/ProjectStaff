package com.savenkov.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataTable extends JPanel {
    String[] columnNames = {
            "Date",
            "Income",
            "Expense",
            "Balance",
            "Description"};

    Object[][] data = {
            { "kek", "kek", "kek", "kek", "kek" }
    };

    public JTable table;

    public void setData(List<DataTableElement> elems) {
        data = elems.stream().map(DataTableElement::renderRaw).collect(Collectors.toList())
                .toArray(new Object[elems.size()][]);

        DefaultTableModel model = (DefaultTableModel)table.getModel();

        while(model.getRowCount() > 0) {
            model.removeRow(model.getRowCount() - 1);
        }

        for(Object[] row : data) {
            model.addRow(row);
        }
        System.out.println(model.getRowCount());

        model.fireTableChanged(null);

        table.setModel(model);

    }

    public DataTable() {

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(650, 200));



        table = new JTable(data, columnNames);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.getColumnModel().getColumn(0).setPreferredWidth(100);
//        table.getColumnModel().getColumn(1).setPreferredWidth(120);
//        table.getColumnModel().getColumn(2).setPreferredWidth(100);
//        table.getColumnModel().getColumn(3).setPreferredWidth(90);
//        table.getColumnModel().getColumn(4).setPreferredWidth(90);
//        table.getColumnModel().getColumn(6).setPreferredWidth(120);
//        table.getColumnModel().getColumn(7).setPreferredWidth(100);
//        table.getColumnModel().getColumn(8).setPreferredWidth(95);
//        table.getColumnModel().getColumn(9).setPreferredWidth(40);
//        table.getColumnModel().getColumn(10).setPreferredWidth(400);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.setModel(tableModel);

        table.setSelectionModel(new DefaultListSelectionModel() {
            {
                setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        });

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
    }
}
