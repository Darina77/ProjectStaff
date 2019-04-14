package com.daryna.View;

import com.daryna.Models.*;
import com.daryna.Models.Data.Employee;
import com.daryna.Models.Data.EmployeeInfoHead;
import com.daryna.Models.Data.EmployeeInfoPos;
import com.daryna.Models.Data.EmployeeInfoWork;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class EmployeesInfoPanel extends JFrame
{

    public EmployeesInfoPanel(ArrayList<EmployeeInfoPos> infoArrayList1,
                              ArrayList<EmployeeInfoWork> infoArrayList2,
                              ArrayList<EmployeeInfoHead> infoArrayList3,
                              int width, int height)
    {
        super("Employee Info");
        JPanel main = new JPanel();
        main.setLayout(new GridBagLayout());
        setSize(width, height);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill=GridBagConstraints.BOTH;
        constraints.weightx=1.0;
        constraints.weighty=0.1;

        JLabel label = new JLabel("Positions, Head and Works Info");
        constraints.gridx=0;
        constraints.gridy=0;
        constraints.gridwidth=3;
        constraints.gridheight=1;
        main.add(label, constraints);

        JTable table = new JTable(new EmployeesInfoPosModel(infoArrayList1));
        constraints.weighty=1.0;
        constraints.gridx=0;
        constraints.gridy=1;
        constraints.gridwidth=1;
        constraints.gridheight=1;
        main.add(new JScrollPane(table), constraints);
        JTable table2 = new JTable(new EmployeesInfoHeadModel(infoArrayList3));
        constraints.gridx=1;
        constraints.gridy=1;
        main.add(new JScrollPane(table2), constraints);
        JTable table3 = new JTable(new EmployeesInfoWorkModel(infoArrayList2));
        constraints.gridx=2;
        constraints.gridy=1;
        main.add(new JScrollPane(table3), constraints);

        add(main);
        setVisible(true);
    }
}


class FindEmployeeInfo extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;

    private JFormattedTextField textField1;
    private JSpinner startDate;
    private JSpinner endDate;
    private Employee selected;
    FindEmployeeInfo(Frame parent, Employee emp) {
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
        setSize(360, 210);
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

        JLabel label2 = new JLabel("Start date");
        content.add(label2);
        startDate = createDateSpinner();
        content.add(startDate);

        JLabel label3 = new JLabel("End date");
        content.add(label3);
        endDate = createDateSpinner();
        content.add(endDate);

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

    public int getEmplId() {
        return (Integer) textField1.getValue();
    }

    public String getStartDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(startDate.getValue());
    }

    public String getEndDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(endDate.getValue());
    }
}
