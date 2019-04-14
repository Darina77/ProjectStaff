package com.daryna.View;

import com.daryna.Controller.EmployeesController;
import com.daryna.Models.*;
import com.daryna.Models.Data.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static javax.swing.JOptionPane.showMessageDialog;

public class EmployeesPanel extends JPanel
{
    EmployeesController controller;
    Employee currentEmp;
    public EmployeesPanel(int width, int height, EmployeesController controller)
    {
        super(new GridBagLayout());
        this.controller = controller;
        GridBagConstraints c = new GridBagConstraints();
        setSize(width, height);
        ArrayList<Employee> employeesArrayList = (ArrayList<Employee>) controller.getAllEmployees();
        TableModel model = new EmployeesModel(employeesArrayList);
        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentEmp = employeesArrayList.get(table.convertRowIndexToModel(table.getSelectedRow()));
            }
        });
        JButton buttonAdd = new JButton("Add");
        JButton buttonDelete = new JButton("Delete");
        JButton buttonSave = new JButton("Save");
        JButton buttonInfo = new JButton("Get Information");

        buttonAdd.addActionListener(e -> {
            final AddEmployee dialog = new AddEmployee((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Add employee");
            if (result == AddEmployee.OK_OPTION) {
                System.out.println("Add employee");
                Employee employee = new Employee(0, dialog.getSurname(), dialog.getSex(), dialog.getDate());
                if (!((EmployeesModel) model).hasData(employee)) {
                    if (controller.addEmployee(employee)) {
                        ((EmployeesModel) model).addData(employee);
                        table.revalidate();
                        showMessageDialog(null, "Add employee");
                    } else {
                        showMessageDialog(null, "Error!");
                    }
                } else {
                    showMessageDialog(null, "Department must be unique!");
                }
            } else {
                System.out.println("User cancelled");
            }
        });

        buttonDelete.addActionListener(e -> {
            final RemoveEmployee dialog = new RemoveEmployee((JFrame) SwingUtilities.getWindowAncestor(this), currentEmp);
            int result = dialog.show("Remove employee");
            if (result == RemoveEmployee.OK_OPTION) {
                System.out.println("Remove employee");
                int idEmp = dialog.getId();
                if (((EmployeesModel) model).hasData(new Employee(idEmp))) {
                    if (controller.removeEmployee(idEmp)) {
                        try {
                            ((EmployeesModel) model).removeData(idEmp);
                            table.revalidate();
                            showMessageDialog(null, "Remove employee");
                        } catch (NoSuchElementException e1) {
                            showMessageDialog(null, "No such employee");
                        }
                    } else {
                        showMessageDialog(null, "Error!");
                    }
                } else {
                    showMessageDialog(null, "No such department");
                }
            } else {
                System.out.println("User cancelled");
            }
        });
        buttonSave.addActionListener(e -> {
           if(table.getCellEditor()!= null) {
               table.getCellEditor().stopCellEditing();
           }

           if (controller.updateEmployee(((EmployeesModel) table.getModel()).getData())) {
               System.out.println("Save complete");
               showMessageDialog(null, "Save complete");
           } else {
               System.out.println("Save error");
               showMessageDialog(null, "Save error");
           }
        });

        buttonInfo.addActionListener(e -> {
            final FindEmployeeInfo dialog = new FindEmployeeInfo((JFrame) SwingUtilities.getWindowAncestor(this), currentEmp);
            int result = dialog.show("Employee info");
            if (result == RemoveDepartment.OK_OPTION) {
                System.out.println("Info employee");
                int emplId = dialog.getEmplId();
                String startDate = dialog.getStartDate();
                String endDate = dialog.getEndDate();

                ArrayList<EmployeeInfoPos> infoArrayList1 = (ArrayList<EmployeeInfoPos>) controller.getEmployeePos(emplId, startDate, endDate);
                ArrayList<EmployeeInfoWork> infoArrayList2 = (ArrayList<EmployeeInfoWork>) controller.getEmployeeWorks(emplId, startDate, endDate);
                ArrayList<EmployeeInfoHead> infoArrayList3 = (ArrayList<EmployeeInfoHead>) controller.getEmployeeHead(emplId, startDate, endDate);

                EmployeesInfoPanel panel1 = new EmployeesInfoPanel(infoArrayList1, infoArrayList2, infoArrayList3, 1100, height);
            } else {
                System.out.println("User cancelled");
            }
        });

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        add(buttonAdd, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        add(buttonDelete, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        add(buttonSave, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        add(buttonInfo, c);
        c.gridy = 1;
        c.gridwidth = 4;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(table), c);
    }
}


class RemoveEmployee extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JFormattedTextField textField1;
    private Employee selected;
    RemoveEmployee(Frame parent, Employee emp) {
        super(parent, true);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 10, 5, 10));
        content = new JPanel(new GridLayout(4, 1));
        content.setBorder(new EmptyBorder(1, 1, 5, 1));
        gui.add(content, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(4));
        gui.add(buttons, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        buttons.add(ok);
        ok.addActionListener(e->{
            result = OK_OPTION;
            setVisible(false);
        });

        JButton cancel = new JButton("Cancel");
        buttons.add(cancel);
        cancel.addActionListener(e->{
            result = CANCEL_OPTION;
            setVisible(false);
        });
        setContentPane(gui);
        setSize(360, 180);
        selected = emp;
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Employee id");
        content.add(label1);
        textField1 = new JFormattedTextField();
        if (selected != null){
            textField1.setValue(new Integer(selected.getId()));
        }

        content.add(textField1);

        setVisible(true);
        return result;
    }

    int getId(){
        return (Integer) textField1.getValue();
    }
}


class AddEmployee extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JTextField textField1;
    private JRadioButton maleRadioBut;
    private JRadioButton femaleRadioBut;
    private JSpinner  date;

    AddEmployee(Frame parent) {
        super(parent, true);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 10, 5, 10));
        content = new JPanel(new GridLayout(5, 2));
        content.setBorder(new EmptyBorder(1, 1, 5, 1));
        gui.add(content, BorderLayout.CENTER);
        JPanel buttons = new JPanel(new FlowLayout(4));
        gui.add(buttons, BorderLayout.SOUTH);

        JButton ok = new JButton("OK");
        buttons.add(ok);
        ok.addActionListener(e->{
            result = OK_OPTION;
            setVisible(false);
        });

        JButton cancel = new JButton("Cancel");
        buttons.add(cancel);
        cancel.addActionListener(e->{
            result = CANCEL_OPTION;
            setVisible(false);
        });
        setContentPane(gui);
        setSize(360, 210);
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Employee`s second name");
        content.add(label1);
        textField1 = new JTextField(15);
        content.add(textField1);

        JLabel label2 = new JLabel("Employee`s sex");
        content.add(label2);
        JLabel label0 = new JLabel("");
        content.add(label0);

        ButtonGroup group = new ButtonGroup();
        maleRadioBut = new JRadioButton("m");
        maleRadioBut.setSelected(true);
        group.add(maleRadioBut);
        content.add(maleRadioBut);

        femaleRadioBut = new JRadioButton("f");
        content.add(femaleRadioBut);
        group.add(femaleRadioBut);

        JLabel label3 = new JLabel("Employee`s birthday");
        content.add(label3);
        date = createDateSpinner();
        content.add(date);

        setVisible(true);
        return result;
    }

    private JSpinner createDateSpinner() {
        SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(spinnerDateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        dateSpinner.setName("date-spinner");
        return dateSpinner;
    }

    String getDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(date.getValue());
    }

    String getSurname()
    {
        return textField1.getText();
    }

    char getSex() { if (maleRadioBut.isSelected()) return 'm'; else  return 'f';}

}


