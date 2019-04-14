package ui.project;

import domain.Employee;
import repository.BossRepository;
import repository.EmployeeRepository;
import ui.DepartmentWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class EditBossWindow {

    private JFrame frame;
    private DepartmentWindow mainWindow;
    private BossRepository bossRepository;
    private EmployeeRepository employeeRepository;
    private int selectedProjectId;
    private int selectedDepartmentId;
    private int selectedEmployeeiId;
    private JLabel message;
    private JButton submitButton;

    public EditBossWindow(DepartmentWindow mainWindow, EmployeeRepository employeeRepository, BossRepository bossRepository, int selectedDepartmentId, int selectedProjectId) {
        this.mainWindow = mainWindow;
        this.bossRepository = bossRepository;
        this.selectedProjectId = selectedProjectId;
        this.selectedDepartmentId = selectedDepartmentId;
        this.employeeRepository = employeeRepository;
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Перевизначити керівника на проекті");
        frame.setSize(400, 160);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        message = new JLabel();
        message.setForeground(Color.red);
        panel.add(message);

        JLabel employeeIdLabel = new JLabel("Співробітник :");
        panel.add(employeeIdLabel);

        JComboBox employeeIdBox = createChooseEmployeeBox();
        panel.add(employeeIdBox);
        panel.add(createSubmitButton(employeeIdBox));

        frame.getContentPane().add(panel);
    }

    private JButton createSubmitButton(JComboBox employeeIdBox) {
        submitButton = new JButton("Призначити!");

        submitButton.addActionListener(e -> {
            try {
                bossRepository.updateBoss(selectedProjectId, Integer.parseInt((String) employeeIdBox.getSelectedItem()));

                employeeIdBox.setEditable(false);
                submitButton.setEnabled(false);

                mainWindow.refreshProjectTable(null);
            } catch (Exception ex) {
                message.setText(ex.getMessage());
            }
        });

        submitButton.setVisible(false);

        return submitButton;
    }

    private JComboBox createChooseEmployeeBox() {

        List<Employee> employees = employeeRepository.getEmployees(selectedDepartmentId);
        JComboBox comboBox = new JComboBox(employees.stream()
                                                   .map(Employee::getId)
                                                   .map(Objects::toString).toArray());

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox) e.getSource();
            selectedEmployeeiId = Integer.valueOf((String) box.getSelectedItem());
            submitButton.setVisible(true);
            message.setText(getInformation(employees.stream()
                                                   .filter(em -> em.getId() == selectedEmployeeiId)
                                                   .findFirst()
                                                   .get()));
        };

        comboBox.addActionListener(actionListener);
        mainWindow.refreshEmployeeTable(null);

        return comboBox;
    }

    private String getInformation(Employee employee) {
        return String.format("Номер: %d%n ПІБ: %s%n Стать: %s%n  Дата народження: %tD%n", employee.getId(),
                             employee.getName(), employee.getGender(), employee.getBirthDate());
    }
}
