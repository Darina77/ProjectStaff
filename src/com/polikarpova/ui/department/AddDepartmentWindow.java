package com.polikarpova.ui.department;

import com.polikarpova.repository.DepartmentRepository;
import com.polikarpova.ui.DepartmentWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddDepartmentWindow {
    private JFrame frame;
    private DepartmentWindow mainWindow;
    private DepartmentRepository departmentRepository;
    JLabel message;

    public AddDepartmentWindow(DepartmentWindow mainWindow, DepartmentRepository departmentRepository) {
        this.mainWindow = mainWindow;
        this.departmentRepository = departmentRepository;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Add stage");
        frame.setSize(400, 160);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        message = new JLabel();
        message.setForeground(Color.red);

        JLabel nameLabel = new JLabel("Назва :");
        panel.add(nameLabel);

        JTextField nameField = new JTextField();
        panel.add(nameField);

        JLabel phoneLabel = new JLabel("Номер телефону:");
        panel.add(phoneLabel);

        JTextField phoneField = new JTextField();
        panel.add(phoneField);

        panel.add(message);
        panel.add(createSubmitButton(nameField, phoneField));

        frame.getContentPane().add(panel);
    }

    private JButton createSubmitButton(JTextField nameField, JTextField phoneField) {
        JButton submitButton = new JButton("Створити");
        submitButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String phoneNumber = phoneField.getText();

                if (name.length() <= 1) throw new Exception("Прізвище має більшу довжину ніж 1");
                if (phoneNumber.length() <= 7)
                    throw new Exception("Номер телефону має складатись з 7 та більше символів");

                departmentRepository.insertDepartment(name, phoneNumber);

                message.setText("Створено!");
                nameField.setEditable(false);
                phoneField.setEditable(false);
                submitButton.setEnabled(false);

                mainWindow.refreshDepartmentTable();
            } catch (Exception ex) {
                message.setText(ex.getMessage());
            }
        });

        return submitButton;
    }
}
