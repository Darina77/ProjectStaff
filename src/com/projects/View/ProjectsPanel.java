package com.projects.View;

import com.projects.Models.Project;
import com.projects.Models.ProjectsModel;
import com.projects.Controller.ProjectsController;

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

public class ProjectsPanel extends JPanel {
    private Project currentProject;
    ProjectsController controller;
    TableModel model;
    JTable table;

    public ProjectsPanel(int width, int height, ProjectsController controller)
    {
        super();
        setSize(width, height);
        setLayout(new GridBagLayout());
        this.controller = controller;
        GridBagConstraints c = new GridBagConstraints();

        ArrayList<Project> projectsArrayList = (ArrayList<Project>) controller.getAllProjects();
        model = new ProjectsModel(projectsArrayList);
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentProject = projectsArrayList.get(table.convertRowIndexToModel(table.getSelectedRow()));
            }
        });
        JButton buttonAdd = new JButton("Add Project");
        JButton buttonDelete = new JButton("Delete Project");
        JButton buttonSave = new JButton("Save Project");

        buttonAdd.addActionListener(e -> {
                final AddProject dialog = new AddProject((JFrame) SwingUtilities.getWindowAncestor(this));
                int result = dialog.show("Add project");
                if (result == AddProject.OK_OPTION) {
                    System.out.println("Add project");
                    Project project = new Project(0,
                            dialog.getProjectName(),
                            dialog.getProjectClient(),
                            dialog.getStartDate(),
                            dialog.getPlanEndDate(),
                            dialog.getRealEndDate(),
                            dialog.getPrice(),
                            dialog.getCost(),
                            dialog.getRating(),
                            dialog.getDepId());
                    if (!((ProjectsModel) model).hasData(project)) {
                        if (controller.addProject(project)) {
                            ((ProjectsModel) model).addData(project);
                            table.revalidate();
                            showMessageDialog(null, "Add project");

                        } else {
                            showMessageDialog(null, "Error!");
                        }
                    } else {
                        showMessageDialog(null, "Project must be unique!");
                    }
                } else {
                    System.out.println("User cancelled");
                }

        });

        buttonDelete.addActionListener(e -> {
            final RemoveProject dialog = new RemoveProject((JFrame) SwingUtilities.getWindowAncestor(this), currentProject);
            int result = dialog.show("Remove project");
            if (result == RemoveProject.OK_OPTION) {
                System.out.println("Remove project");
                int idProject = dialog.getId();
                if (((ProjectsModel) model).hasData(new Project(idProject))) {
                    if (controller.removeProject(idProject)) {
                        try {
                            ((ProjectsModel) model).removeData(idProject);
                            table.revalidate();
                            showMessageDialog(null, "Remove project");
                        } catch (NoSuchElementException e1) {
                            showMessageDialog(null, "No such project");
                        }
                    } else {
                        showMessageDialog(null, "Error!");
                    }
                } else {
                    showMessageDialog(null, "No such project");
                }
            } else {
                System.out.println("User cancelled");
            }
        });
        buttonSave.addActionListener(e -> {
            if(table.getCellEditor()!= null) {
                table.getCellEditor().stopCellEditing();
            }

            if (controller.updateProjects(((ProjectsModel) table.getModel()).getData())) {
                System.out.println("Save complete");
                showMessageDialog(null, "Save complete");
            } else {
                System.out.println("Save error");
                showMessageDialog(null, "Save error");
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
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(table), c);
    }
}

class RemoveProject extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JFormattedTextField textField1;
    private Project selected;
    RemoveProject(Frame parent, Project project) {
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
        selected = project;
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Project id");
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


class AddProject extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JTextField textField1;
    private JTextField textField11;
    private JSpinner  date;
    private JSpinner  date1;
    private JSpinner  date2;
    private JSpinner textField2;
    private JSpinner textField3;
    private JTextField textField4;
    private JSpinner textField5;

    AddProject(Frame parent) {
        super(parent, true);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 10, 5, 10));
        content = new JPanel(new GridLayout(9, 2));
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
        setSize(320, 310);
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();
        JLabel label1 = new JLabel("Name");
        content.add(label1);
        textField1 = new JTextField();
        content.add(textField1);

        JLabel label11 = new JLabel("Client");
        content.add(label11);
        textField11 = new JTextField();
        content.add(textField11);

        JLabel label3 = new JLabel("Start Date");
        content.add(label3);
        date = createDateSpinner();
        content.add(date);

        JLabel label4 = new JLabel("Plan End Date");
        content.add(label4);
        date1 = createDateSpinner();
        content.add(date1);

        JLabel label5 = new JLabel("Real End Date");
        content.add(label5);
        date2 = createDateSpinner();
        content.add(date2);
        SpinnerModel model2 = new SpinnerNumberModel(1000, 1, 1000000000, 0.1);
        JLabel label6 = new JLabel("Price");
        content.add(label6);
        textField2 = new JSpinner(model2);
        content.add(textField2);

        SpinnerModel model3 = new SpinnerNumberModel(1000, 1, 1000000000, 0.1);
        JLabel label7 = new JLabel("Cost");
        content.add(label7);
        textField3 = new JSpinner(model3);
        content.add(textField3);

        JLabel label9 = new JLabel("Rating");
        content.add(label9);
        textField4 = new JTextField();
        content.add(textField4);

        SpinnerModel model4 = new SpinnerNumberModel(1, 1, 1000000000, 1);
        JLabel label8 = new JLabel("Department id");
        content.add(label8);
        textField5 = new JSpinner(model4);
        content.add(textField5);

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

    String getStartDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(date.getValue());
    }

    String getPlanEndDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(date1.getValue());
    }

    String getRealEndDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(date2.getValue());
    }

    String getProjectClient()
    {
        return textField11.getText();
    }
    String getRating()
    {
        return textField4.getText();
    }
    int getDepId() { return (int)textField5.getValue(); }
    String getProjectName()
    {
        return textField1.getText();
    }
    double getPrice()
    {
        return (double)textField2.getValue();
    }
    double getCost()
    {
        return (double)textField3.getValue();
    }


}
