package View;

import Models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

public class EmployeesPanel extends JPanel
{
    public EmployeesPanel(int width, int height)
    {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        setSize(width, height);
        ArrayList<Employee> employeesArrayList = new ArrayList<>();

        for (int i = 0; i < 25; i++)
        {
            try {
                employeesArrayList.add(new Employee(i, "Surname"+i, 's', "11.11.2011"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        JButton buttonAdd = new JButton("Add");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        buttonAdd.addActionListener(e -> {
            final AddEmployee dialog = new AddEmployee((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Add employee");
            if (result == AddEmployee.OK_OPTION) {
                System.out.println("Add employee");
                showMessageDialog(null, "Add employee");
            } else {
                System.out.println("User cancelled");
            }
        });
        add(buttonAdd, c);

        JButton buttonDelete = new JButton("Delete");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        buttonDelete.addActionListener(e -> {
            final RemoveEmployee dialog = new RemoveEmployee((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Remove employee");
            if (result == RemoveEmployee.OK_OPTION) {
                System.out.println("Remove employee");
                showMessageDialog(null, "Remove employee");
            } else {
                System.out.println("User cancelled");
            }
        });
        add(buttonDelete, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(e -> {
            System.out.println("Save complete");
            showMessageDialog(null, "Save complete");
        });
        add(buttonSave,  c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        JButton buttonInfo = new JButton("Get Information");
        buttonInfo.addActionListener(e -> {
            final FindEmployeeInfo dialog = new FindEmployeeInfo((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Employee info");
            if (result == RemoveDepartment.OK_OPTION) {
                System.out.println("Info employee");
                ArrayList<EmployeeInfoPos> infoArrayList1 = new ArrayList<>();
                ArrayList<EmployeeInfoWork> infoArrayList2 = new ArrayList<>();
                ArrayList<EmployeeInfoHead> infoArrayList3 = new ArrayList<>();
                for (int i = 0; i < 25; i++) {
                    try {
                        infoArrayList1.add(new EmployeeInfoPos("Position" + i, "Description" + i, "11.11.2011", "11.11.2011", 1000 * i));
                        infoArrayList2.add(new EmployeeInfoWork("Description" + 1, "ProjectName" + i, 10, "11.11.2011", "11.11.2011"));
                        infoArrayList3.add(new EmployeeInfoHead("ProjectName" + i, "11.11.2011", "11.11.2011", 1000 * i));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
                EmployeesInfoPanel panel1 = new EmployeesInfoPanel(new EmployeesInfoPosModel(infoArrayList1), "Employee`s positions", width, height);
                EmployeesInfoPanel panel2 = new EmployeesInfoPanel(new EmployeesInfoWorkModel(infoArrayList2), "Employee`s works", width, height);
                EmployeesInfoPanel panel3 = new EmployeesInfoPanel(new EmployeesInfoHeadModel(infoArrayList3), "Head", width, height);
            } else {
                System.out.println("User cancelled");
            }
        });
        add(buttonInfo,  c);

        TableModel model = new EmployeesModel(employeesArrayList);
        JTable table = new JTable(model);
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

    RemoveEmployee(Frame parent) {
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
    }

    int show(String title) {
        setTitle(title);
        content.removeAll();

        JLabel label1 = new JLabel("Employee id");
        content.add(label1);
        JTextField textField1 = new JTextField(15);
        content.add(textField1);

        setVisible(true);
        return result;
    }

}


class AddEmployee extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;

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
        JTextField textField1 = new JTextField(15);
        content.add(textField1);
        JLabel label2 = new JLabel("Employee`s sex");
        content.add(label2);
        JLabel label0 = new JLabel("");
        content.add(label0);
        ButtonGroup group = new ButtonGroup();
        JRadioButton maleRadioBut = new JRadioButton("m");
        maleRadioBut.setSelected(true);
        group.add(maleRadioBut);
        content.add(maleRadioBut);
        JRadioButton famaleRadioBut = new JRadioButton("f");
        content.add(famaleRadioBut);
        group.add(famaleRadioBut);
        JLabel label3 = new JLabel("Employee`s birthday");
        content.add(label3);
        JFormattedTextField ftf = new JFormattedTextField();
        ftf.setValue(new Date());
        content.add(ftf);

        setVisible(true);
        return result;
    }
}


