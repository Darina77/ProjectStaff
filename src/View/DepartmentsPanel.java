package View;

import Models.Department;
import Models.DepartmentInfo;
import Models.DepartmentsModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

public class DepartmentsPanel extends JPanel
{
    public DepartmentsPanel(int width, int height)
    {
        super(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        setSize(width, height);
        ArrayList<Department> departmentArrayList = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            departmentArrayList.add(new Department("Name " + i, "7895-586" + i));
        }

        JButton buttonAdd = new JButton("Add");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        buttonAdd.addActionListener(e -> {
            final AddDepartment dialog = new AddDepartment((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Add department");
            if (result == AddDepartment.OK_OPTION) {
                System.out.println("Add department");
                showMessageDialog(null, "Add department");
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
            final RemoveDepartment dialog = new RemoveDepartment((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Remove department");
            if (result == RemoveDepartment.OK_OPTION) {
                System.out.println("Remove department");
                showMessageDialog(null, "Remove department");
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
            final FindDepartmentInfo dialog = new FindDepartmentInfo((JFrame) SwingUtilities.getWindowAncestor(this));
            int result = dialog.show("Department info");
            if (result == RemoveDepartment.OK_OPTION) {
                System.out.println("Info department");
                ArrayList<DepartmentInfo> departmentInfoArrayList = new ArrayList<>();

                for (int i = 0; i < 25; i++) {
                    departmentInfoArrayList.add(new DepartmentInfo("Position"+i, 1000*i, i, "Surname"+i));
                }
                DepartmentsInfoPanel depInfoPanel = new DepartmentsInfoPanel(departmentInfoArrayList, width, height);
            } else {
                System.out.println("User cancelled");
            }
        });
        add(buttonInfo,  c);

        TableModel model = new DepartmentsModel(departmentArrayList);
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


class RemoveDepartment extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;

    RemoveDepartment(Frame parent) {
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

        JLabel label1 = new JLabel("Department name");
        content.add(label1);
        JTextField textField1 = new JTextField(15);
        content.add(textField1);

        setVisible(true);
        return result;
    }

}


class AddDepartment extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;

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

        JLabel label1 = new JLabel("Department name");
        content.add(label1);
        JTextField textField1 = new JTextField(15);
        content.add(textField1);
        JLabel label2 = new JLabel("Department phone number");
        content.add(label2);
        JTextField textField2 = new JTextField(15);
        content.add(textField2);

        setVisible(true);
        return result;
    }

}