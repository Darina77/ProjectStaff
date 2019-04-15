package com.rozhko.View;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Objects;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.rozhko.Controller.DbAccess;
import com.rozhko.Models.Data;
import com.rozhko.Models.Item;
import com.rozhko.Models.MyComboBoxModel;
import com.rozhko.Models.MyTableModel;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class WorksWindow {

	public JFrame frame;
	private JTable table;
	MyComboBoxModel modelProjects;
	MyComboBoxModel modelStages;
	MyTableModel modelTable;
	JTextField textField;
	JLabel amount;

	private static DbAccess db = null;

	/**
	 * Create the application.
	 */
	public WorksWindow() {
		db = new DbAccess();
		db.connectionDb("Projects");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500);

		JPanel tasksPanel = new JPanel();
		tasksPanel.setLayout(null);
		
		amount = new JLabel("0");
		amount.setBounds(678, 390, 61, 16);
		tasksPanel.add(amount);
		
		tasksPanel.add(createInput());
		tasksPanel.add(createTable());
		tasksPanel.add(createProjectsSelect());
		tasksPanel.add(createStagesSelect());
		
		JButton button = new JButton("Додати");
		button.setBounds(6, 385, 117, 29);
		tasksPanel.add(button);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Create window = new Create(db);
				window.frame.setVisible(true);
			}
			
		});
		
		frame.getContentPane().add(tasksPanel);
		
		JLabel label = new JLabel("Сума:");
		label.setBounds(632, 390, 43, 16);
		tasksPanel.add(label);
	}

	private JTextField createInput() {
		textField = new JTextField("Введіть імя співробітника");
		textField.setBounds(6, 6, 243, 29);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});
		
		return textField;
	}

	private JTable createTable() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 400, 761, -344);
		
		Vector<Data> tableData = db.getTable("", "", "");
		
		modelTable = new MyTableModel(tableData);
		setAmount(tableData);

		table = new JTable(modelTable);
		table.setPreferredScrollableViewportSize(new Dimension(755, 346));
		table.setBounds(6, 68, 755, 310);

		return table;
	}

	private JComboBox createProjectsSelect() {
		Vector<Item> projectsList = db.getProjects();
		projectsList.add(0, new Item("", "Всі"));

		modelProjects = new MyComboBoxModel(projectsList);

		JComboBox comboBox = new JComboBox(modelProjects);
		comboBox.setBounds(482, 6, 129, 29);

		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Item selected = (Item) cb.getSelectedItem();
				String id = selected.getId();
				updateTable();
				updateStages(id);

			}
		});

		return comboBox;
	}

	private void updateStages(String idPrj) {
		Vector<Item> stages = db.getStagesByProjectId(idPrj);
		modelStages.removeAllElements();
		modelStages.addElement(new Item("", "Всі"));
		for (int i = 0; i < stages.size(); i++) {
			modelStages.addElement(new Item(stages.get(i).getId(), stages.get(i).getValue()));
		}
	}

	private JComboBox createStagesSelect() {
		modelStages = new MyComboBoxModel();

		JComboBox comboBox_1 = new JComboBox(modelStages);
		comboBox_1.setBounds(632, 7, 129, 27);

		comboBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});

		return comboBox_1;
	}
	
	private void updateTable() {
		String idPrj = modelProjects.getSelectedItem().getId();
		String idStg = "";
		if(modelStages.getSelectedItem() != null) {
			idStg = modelStages.getSelectedItem().getId();
		}
		
		String name = "";
		if(!Objects.equals("Введіть імя співробітника", textField.getText())) {
			name = textField.getText();
			
		}
		
		Vector<Data> tableData = db.getTable(name, idPrj, idStg);
		modelTable.updateData(tableData);
		setAmount(tableData);
	}
	
	
	private void setAmount(Vector<Data> data) {
		float sum = 0;
		
		Iterator value = data.iterator(); 
		   
        while (value.hasNext()) { 
        	Data v = (Data) value.next();
        	sum += Float.parseFloat(v.getPrice());
        } 
        
        amount.setText(Float.toString(sum));
	}
}
