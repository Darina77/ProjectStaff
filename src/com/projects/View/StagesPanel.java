package com.projects.View;

import com.projects.Models.Stages;
import com.projects.Models.StagesModel;
import com.projects.Controller.StagesController;
import com.rozhko.Models.Item;
import com.rozhko.Models.MyComboBoxModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Vector;

import static javax.swing.JOptionPane.showMessageDialog;

public class StagesPanel extends JPanel {
    static Integer selectedProjectId = null;

    private Stages currentStage;
    StagesController controller;
    TableModel model;
    JTable table;
    MyComboBoxModel modelProjects;
    JComboBox comboBox;
    public StagesPanel(int width, int height, StagesController controller)
    {
        super();
        setSize(width, height);
        setLayout(new GridBagLayout());
        this.controller = controller;
        GridBagConstraints c = new GridBagConstraints();

        ArrayList<Stages> stagesArrayList = (ArrayList<Stages>) controller.getAllStages();
        model = new StagesModel(stagesArrayList);
        table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                currentStage = stagesArrayList.get(table.convertRowIndexToModel(table.getSelectedRow()));
            }
        });
        JButton buttonAdd = new JButton("Add Stage");
        JButton buttonDelete = new JButton("Delete Stage");
        JButton buttonSave = new JButton("Save Stage");

        buttonAdd.addActionListener(e -> {
            if (selectedProjectId != null) {
                final AddStage dialog = new AddStage((JFrame) SwingUtilities.getWindowAncestor(this));
                int result = dialog.show("Add stage");
                if (result == AddStage.OK_OPTION) {
                    System.out.println("Add stage");
                    Stages stage = new Stages(0, dialog.getNumber(), dialog.getStartDate(), dialog.getPlanEndDate(), dialog.getRealEndDate(),
                            dialog.getPrice(), dialog.getCost(), selectedProjectId);
                    if (!((StagesModel) model).hasData(stage)) {
                        if (controller.addStage(stage)) {
                            ((StagesModel) model).addData(stage);
                            table.revalidate();
                            this.revalidate();
                            showMessageDialog(null, "Add stage");
                        } else {
                            showMessageDialog(null, "Error!");
                        }
                    } else {
                        showMessageDialog(null, "Stage must be unique!");
                    }
                } else {
                    System.out.println("User cancelled");
                }
            } else {
                showMessageDialog(null, "Choose project!");
            }
        });

        buttonDelete.addActionListener(e -> {
            final RemoveStage dialog = new RemoveStage((JFrame) SwingUtilities.getWindowAncestor(this), currentStage);
            int result = dialog.show("Remove stage");
            if (result == RemoveStage.OK_OPTION) {
                System.out.println("Remove stage");
                int idStage = dialog.getId();
                if (((StagesModel) model).hasData(new Stages(idStage))) {
                    if (controller.removeStage(idStage)) {
                        try {
                            ((StagesModel) model).removeData(idStage);
                            table.revalidate();
                            showMessageDialog(null, "Remove stage");
                        } catch (NoSuchElementException e1) {
                            showMessageDialog(null, "No such stage");
                        }
                    } else {
                        showMessageDialog(null, "Error!");
                    }
                } else {
                    showMessageDialog(null, "No such stage");
                }
            } else {
                System.out.println("User cancelled");
            }
        });
        buttonSave.addActionListener(e -> {
            if(table.getCellEditor()!= null) {
                table.getCellEditor().stopCellEditing();
            }

            if (controller.updateStage(((StagesModel) table.getModel()).getData())) {
                System.out.println("Save complete");
                showMessageDialog(null, "Save complete");
            } else {
                System.out.println("Save error");
                showMessageDialog(null, "Save error");
            }
        });
        Vector<Item> projectsList = controller.getAllProjects();

        projectsList.add(0, new Item("", "All projects"));

        modelProjects = new MyComboBoxModel(projectsList);

        comboBox = new JComboBox(modelProjects);
        comboBox.setBounds(482, 6, 129, 29);

        comboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            Item selected = (Item) cb.getSelectedItem();
            if (selected != null) {
                String id = selected.getId();
                ((StagesModel) model).update(id);
                table.revalidate();
                if (id == "") {
                    selectedProjectId = null;
                } else {
                    selectedProjectId = Integer.parseInt(id);
                }
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
        add(comboBox, c);
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(table), c);
    }

    public void reloadProjects()
    {
        Vector<Item> projectsList = controller.getAllProjects();

        projectsList.add(0, new Item("", "All projects"));

        modelProjects.removeAllElements();
        for (Item i : projectsList)
        {
            modelProjects.addElement(i);
        }

        comboBox.revalidate();
    }
}

class RemoveStage extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JFormattedTextField textField1;
    private Stages selected;
    RemoveStage(Frame parent, Stages stage) {
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
        selected = stage;
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Stage id");
        content.add(label1);
        textField1 = new JFormattedTextField();
        if (selected != null){
            textField1.setValue(new Integer(selected.getIdStage()));
        }

        content.add(textField1);

        setVisible(true);
        return result;
    }

    int getId(){
        return (Integer) textField1.getValue();
    }
}


class AddStage extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JSpinner textField1;
    private JSpinner  date;
    private JSpinner  date1;
    private JSpinner  date2;
    private JSpinner textField2;
    private JSpinner textField3;

    AddStage(Frame parent) {
        super(parent, true);

        JPanel gui = new JPanel(new BorderLayout(3, 3));
        gui.setBorder(new EmptyBorder(5, 10, 5, 10));
        content = new JPanel(new GridLayout(6, 2));
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
        SpinnerModel model = new SpinnerNumberModel(1, 1, 1000000, 1);
        JLabel label1 = new JLabel("Number");
        content.add(label1);
        textField1 = new JSpinner(model);
        content.add(textField1);

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

    int getNumber()
    {
        return (int)textField1.getValue();
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
