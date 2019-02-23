package View;

import Models.Data.DepartmentInfo;
import Models.DepartmentInfoModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DepartmentsInfoPanel extends JFrame
{
    public DepartmentsInfoPanel(ArrayList<DepartmentInfo> departmentInfoArrayList, String depName, int width, int height)
    {
        super("Departments info");
        JPanel main = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        setSize(width, height);
        JLabel label = new JLabel(depName);
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        main.add(label);
        TableModel model = new DepartmentInfoModel(departmentInfoArrayList);
        JTable table = new JTable(model);
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        main.add(new JScrollPane(table), c);
        add(main);
        setVisible(true);
    }
}



class FindDepartmentInfo extends JDialog
{
    static final int OK_OPTION = 0;
    private static final int CANCEL_OPTION = 1;

    private int result = -1;

    private JPanel content;
    private JTextField textField1;
    private JSpinner  date;

    FindDepartmentInfo(Frame parent) {
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
        textField1 = new JTextField(15);
        content.add(textField1);
        JLabel label2 = new JLabel("Date");
        content.add(label2);
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

    String getDepName()
    {
        return textField1.getText();
    }
}
