package com.rozhko.Models;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel {
	String[] columnNames = { "Імя співробітника", "Проект", "Етап", "Дата початку", "Дата закінчення", "Винагорода", };

	Vector<Data> data;

	public MyTableModel(Vector<Data> data) {
		super();
		this.data = data;
	}
	
	public void updateData(Vector<Data> data) {
		this.data = data;
		this.fireTableDataChanged();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return data.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Class getColumnClass(int col) {
		return String.class;
	}

	public Object getValueAt(int row, int col) {
		Data ss = (Data) (data.elementAt(row));

		switch (col) {
		case 0:
			return ss.getName();
		case 1:
			return ss.getProject();
		case 2:
			return ss.getStage();
		case 3:
			return ss.getStartDate();
		case 4:
			return ss.getEndDate();
		case 5:
			return ss.getPrice();
		}

		return new String();
	}

}