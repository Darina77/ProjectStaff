package com.daryna.View;

import com.daryna.Controller.DepartmentsController;
import com.daryna.Models.Data.Department;
import com.daryna.Models.Data.DepartmentInfo;
import com.daryna.Models.DepartmentsModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static javax.swing.JOptionPane.showMessageDialog;

public class DepartmentsPanel extends JPanel
{
    DepartmentsController controller;
    Department currentDep;

    public DepartmentsPanel(int width, int height, DepartmentsController controller) {
        super(new GridBagLayout());
        this.controller = controller;
        GridBagConstraints c = new GridBagConstraints();
        setSize(width, height);
        ArrayList<Department> departmentArrayList = (ArrayList<Department>) this.controller.getAllDepartments();
        TableModel model = new DepartmentsModel(departmentArrayList);
        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentDep = departmentArrayList.get(table.convertRowIndexToModel(table.getSelectedRow()));
            }
        });
        JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(e -> {
            final AddDepartment dialog = new AddDepartment((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Add department");
            if (result == AddDepartment.OK_OPTION) {
                System.out.println("Add department");
                Department dep = dialog.getVal();
                if (!((DepartmentsModel) model).hasData(dep)) {
                    if (controller.addDepartmetn(dep)) {
                        try {
                            ((DepartmentsModel) model).addData(dep);
                            table.revalidate();
                            showMessageDialog(null, "Add department");
                        } catch (CloneNotSupportedException e1) {
                            showMessageDialog(null, "Department must be unique!");
                        }
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

        JButton buttonDelete = new JButton("Delete");
        buttonDelete.addActionListener(e -> {

            final RemoveDepartment dialog = new RemoveDepartment((JFrame) SwingUtilities.getWindowAncestor(this), currentDep);
            int result = dialog.show("Remove department");

            if (result == RemoveDepartment.OK_OPTION) {
                System.out.println("Remove department");
                int depId = dialog.getVal();
                if (((DepartmentsModel) model).hasData(new Department(depId, "", ""))) {
                    if (controller.removeDepartment(depId)) {
                        try {
                            ((DepartmentsModel) model).removeData(depId);
                            table.revalidate();
                            showMessageDialog(null, "Remove department");
                        } catch (NoSuchElementException e1) {
                            showMessageDialog(null, "No such department");
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

        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(e -> {
            if(table.getCellEditor()!= null) {
                table.getCellEditor().stopCellEditing();
            }
            if (controller.updateDep(((DepartmentsModel) table.getModel()).getData())) {
                System.out.println("Save complete");
                showMessageDialog(null, "Save complete");
            } else {
                System.out.println("Save error");
                showMessageDialog(null, "Save error");
            }

        });

        JButton buttonInfo = new JButton("Get Information");
        buttonInfo.addActionListener(e -> {
            final FindDepartmentInfo dialog = new FindDepartmentInfo((JFrame) SwingUtilities.getWindowAncestor(this), currentDep);
            int result = dialog.show("Department info");
            if (result == RemoveDepartment.OK_OPTION) {
                String depName = dialog.getDepName();
                String date = dialog.getDate();
                if (!depName.equals("") || !date.equals("")) {
                    System.out.println("Info department");
                    ArrayList<DepartmentInfo> departmentInfoArrayList =
                            (ArrayList<DepartmentInfo>) this.controller.getDepInfo(depName, date);
                    DepartmentsInfoPanel depInfoPanel = new DepartmentsInfoPanel(departmentInfoArrayList, depName, width, height);
                } else {
                    showMessageDialog(null, "Empty values! Write smth!");
                }
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


class RemoveDepartment extends JDialog {
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JTextField textField1;
    private Department selectedDep;

    RemoveDepartment(Frame parent, Department dep) {
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
        ok.addActionListener(e -> {
            result = OK_OPTION;
            setVisible(false);
        });

        JButton cancel = new JButton("Cancel");
        buttons.add(cancel);
        cancel.addActionListener(e -> {
            result = CANCEL_OPTION;
            setVisible(false);
        });
        setContentPane(gui);
        setSize(360, 180);
        selectedDep = dep;
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Department id");
        content.add(label1);
        textField1 = new JTextField(15);
        if (selectedDep != null) {
            textField1.setText(selectedDep.getId()+"");
        }
        content.add(textField1);

        setVisible(true);
        return result;
    }

    int getVal() {
        if (textField1 != null) {
            return Integer.parseInt(textField1.getText());
        } else {
            return -1;
        }
    }

}


class AddDepartment extends JDialog {
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JTextField textField1;
    private JTextField textField2;

    AddDepartment(Frame parent) {
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
        ok.addActionListener(e -> {
            result = OK_OPTION;
            setVisible(false);
        });

        JButton cancel = new JButton("Cancel");
        buttons.add(cancel);
        cancel.addActionListener(e -> {
            result = CANCEL_OPTION;
            setVisible(false);
        });
        setContentPane(gui);
        setSize(360, 210);
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Department name");
        content.add(label1);
        textField1 = new JTextField(15);
        content.add(textField1);
        JLabel label2 = new JLabel("Department phone number");
        content.add(label2);
        textField2 = new JTextField(15);
        content.add(textField2);

        setVisible(true);
        return result;
    }

    public Department getVal() {
        if (textField1 != null && textField2 != null) {
            return new Department(0, textField1.getText(), textField2.getText());
        } else return new Department(0,"", "");
    }

}