package com.rozhko.View;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.rozhko.Controller.DbAccess;
import com.rozhko.Models.Item;
import com.rozhko.Models.MyComboBoxModel;
import com.savenkov.models.Transaction;

import java.awt.Button;
import javax.swing.JButton;

public class Create {

	public JFrame frame;
	private JTextField startDate;
	private JTextField endDate;
	private JTextField desc;
	
	private DbAccess db = null;
	private MyComboBoxModel modelProjects;
	private MyComboBoxModel modelStages;
	private MyComboBoxModel modelEmp;
	private JTextField reward;

	/**
	 * Create the application.
	 */
	public Create(DbAccess db) {
		this.db = db;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.getContentPane().setLayout(null);
		
		Vector<Item> empList = db.getEmpoyees();

		modelEmp = new MyComboBoxModel(empList);
		
		JComboBox emp = new JComboBox(modelEmp);
		emp.setBounds(199, 29, 192, 27);
		frame.getContentPane().add(emp);
		
		JLabel label = new JLabel("Працівник");
		label.setBounds(79, 33, 108, 16);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Дата початку");
		label_1.setBounds(79, 61, 108, 16);
		frame.getContentPane().add(label_1);
		
		startDate = new JTextField();
		startDate.setBounds(199, 56, 192, 26);
		frame.getContentPane().add(startDate);
		startDate.setColumns(10);
		
		JLabel label_2 = new JLabel("Дата кінця");
		label_2.setBounds(79, 89, 108, 16);
		frame.getContentPane().add(label_2);
		
		endDate = new JTextField();
		endDate.setBounds(199, 84, 192, 26);
		frame.getContentPane().add(endDate);
		endDate.setColumns(10);
		
		JLabel label_3 = new JLabel("Опис");
		label_3.setBounds(79, 117, 61, 16);
		frame.getContentPane().add(label_3);
		
		desc = new JTextField();
		desc.setBounds(199, 112, 192, 26);
		frame.getContentPane().add(desc);
		desc.setColumns(10);
		
		JLabel label_4 = new JLabel("Проект");
		label_4.setBounds(79, 145, 61, 16);
		frame.getContentPane().add(label_4);
		
		JLabel label_5 = new JLabel("Етап");
		label_5.setBounds(79, 173, 61, 16);
		frame.getContentPane().add(label_5);
		
		Vector<Item> projectsList = db.getProjects();
		projectsList.add(0, new Item("", "Оберіть проект"));

		modelProjects = new MyComboBoxModel(projectsList);
		
		JComboBox comboBox_1 = new JComboBox(modelProjects);
		comboBox_1.setBounds(199, 141, 192, 27);
		frame.getContentPane().add(comboBox_1);
		
		comboBox_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Item selected = (Item) cb.getSelectedItem();
				String id = selected.getId();
				updateStages(id);

			}
		});
		
		modelStages = new MyComboBoxModel();
		
		JComboBox comboBox_2 = new JComboBox(modelStages);
		comboBox_2.setBounds(199, 169, 192, 27);
		frame.getContentPane().add(comboBox_2);
		
		JButton button = new JButton("Додати");
		button.setBounds(20, 232, 117, 29);
		frame.getContentPane().add(button);
		
		JLabel label_6 = new JLabel("Винагорода");
		label_6.setBounds(79, 201, 108, 16);
		frame.getContentPane().add(label_6);
		
		reward = new JTextField();
		reward.setBounds(199, 196, 192, 26);
		frame.getContentPane().add(reward);
		reward.setColumns(10);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Item empItem = (Item)emp.getSelectedItem();
				String empId = empItem.getId();
				
				String startDateN = startDate.getText();
				String endDateN = endDate.getText();
				String descN = desc.getText();
				String rewardN = reward.getText();
				
				String stage = modelStages.getSelectedItem().getId();
				
				
				int workId = db.createTask(empId, startDateN, endDateN, descN, rewardN, stage);

//				int transactionId = com.savenkov.dao.Dao.createTransaction(new Transaction(
//						-1,
//						-Double.parseDouble(rewardN),
//						new Date(), null, "Payment to employee: " + descN), false);
//
//				com.savenkov.dao.Dao.createPaymentToEmployee(workId, transactionId, Integer.parseInt(stage));
				
				frame.setVisible(false);
			}
			
		});
		
	}
	
	private void updateStages(String idPrj) {
		Vector<Item> stages = db.getStagesByProjectId(idPrj);
		modelStages.removeAllElements();
		modelStages.addElement(new Item("", "Оберіть етап"));
		for (int i = 0; i < stages.size(); i++) {
			modelStages.addElement(new Item(stages.get(i).getId(), stages.get(i).getValue()));
		}
	}

}
