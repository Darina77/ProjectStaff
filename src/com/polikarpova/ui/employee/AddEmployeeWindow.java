package ui.employee;

import domain.Department;
import repository.DepartmentRepository;
import repository.PositionRepository;
import ui.DepartmentWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Objects;

public class AddEmployeeWindow {

    private JFrame frame;
    private DepartmentWindow mainWindow;
    private PositionRepository positionRepository;
    private DepartmentRepository departmentRepository;
    private int selectedEmployeeId;
    private int selectedDepartmentId;
    private JLabel message;
    private JButton submitButton;

    public AddEmployeeWindow(DepartmentWindow mainWindow, PositionRepository positionRepository, DepartmentRepository departmentRepository, int selectedEmployeeId) {
        this.mainWindow = mainWindow;
        this.positionRepository = positionRepository;
        this.selectedEmployeeId = selectedEmployeeId;
        this.departmentRepository = departmentRepository;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Додати співробітника до відділу");
        frame.setSize(400, 160);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        message = new JLabel();
        message.setForeground(Color.red);

        JLabel departmentIdLabel = new JLabel("Номер відділу :");
        panel.add(departmentIdLabel);

        JComboBox departmentIdBox = createChooseDepartmentBox();
        panel.add(departmentIdBox);

        JLabel descriptionLabel = new JLabel("Опис посади:");
        panel.add(descriptionLabel);

        JTextField descriptionField = new JTextField();
        panel.add(descriptionField);

        JLabel salaryLabel = new JLabel("Оклад:");
        panel.add(salaryLabel);

        JTextField salaryField = new JTextField();
        panel.add(salaryField);

        panel.add(message);
        panel.add(createSubmitButton(departmentIdBox, descriptionField, salaryField));

        frame.getContentPane().add(panel);
    }

    private JButton createSubmitButton(JComboBox departmentIdField, JTextField descriptionField, JTextField salaryField) {
        submitButton = new JButton("Створити");

        submitButton.addActionListener(e -> {
            try {

                String description = descriptionField.getText();
                BigDecimal salary = new BigDecimal(salaryField.getText());

                if (salary.intValue() <= 0)
                    throw new Exception("Оклад повинен бути більший за 0!");

                positionRepository.createPosition(description, salary, selectedDepartmentId, selectedEmployeeId);

                message.setText("Створено!");

                departmentIdField.setEditable(false);
                descriptionField.setEditable(false);
                salaryField.setEditable(false);
                submitButton.setEnabled(false);

                mainWindow.refreshEmployeeTable(null);
            } catch (Exception ex) {
                message.setText(ex.getMessage());
            }
        });

        submitButton.setVisible(false);

        return submitButton;
    }

    private JComboBox createChooseDepartmentBox() {

        JComboBox comboBox = new JComboBox(departmentRepository.getDepartments().stream()
                                                   .map(Department::getDepartmentId)
                                                   .map(Objects::toString).toArray());

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox) e.getSource();
            selectedDepartmentId = Integer.valueOf((String) box.getSelectedItem());
            submitButton.setVisible(true);
        };

        comboBox.addActionListener(actionListener);
        mainWindow.refreshEmployeeTable(null);

        return comboBox;
    }
}
