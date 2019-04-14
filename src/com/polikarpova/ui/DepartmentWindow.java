package com.polikarpova.ui;

import com.polikarpova.domain.*;
import com.polikarpova.repository.*;
import com.polikarpova.ui.department.AddDepartmentWindow;
import com.polikarpova.ui.department.EditDepartmentWindow;
import com.polikarpova.ui.employee.AddEmployeeWindow;
import com.polikarpova.ui.employee.EditEmployeeWindow;
import com.polikarpova.ui.project.AddBossWindow;
import com.polikarpova.ui.project.EditBossWindow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DepartmentWindow {
    private DepartmentWindow mainWindow;
    private DepartmentRepository departmentRepository;
    private EmployeeRepository employeeRepository;

    private JTable departmentTable;
    private JButton deleteDepartmentButton;
    private JButton editDepartmentButton;
    private JButton addEmployeeButton;
    private JTable employeeTable;
    private PositionRepository positionRepository;

    private JTable projectTable;
    private JButton addBossButton;
    private ProjectRepository projectRepository;
    private BossRepository bossRepository;
    private JButton editBossButton;
    private JButton editEmployeeButton;

    public DepartmentWindow() {
        mainWindow = this;
        ConnectionManager connectionManager = new ConnectionManager();
        //connectionManager.connect("projects", "root", "qwertyuiop");
        connectionManager.connect("Projects", "root", "123456");

        departmentRepository = new DepartmentRepository(connectionManager);
        employeeRepository = new EmployeeRepository(connectionManager);
        positionRepository = new PositionRepository(connectionManager);
        projectRepository = new ProjectRepository(connectionManager);
        bossRepository = new BossRepository(connectionManager);

        initialize();
    }

    public void refreshDepartmentTable() {
        departmentTable.setModel(fillDepartmentTable((DefaultTableModel) departmentTable.getModel()));
    }

    public void refreshEmployeeTable(Integer selected) {
        employeeTable.setModel(fillEmployeeTable((DefaultTableModel) employeeTable.getModel(), selected));
    }

    public void refreshProjectTable(Integer selected) {
        projectTable.setModel(fillProjectTable((DefaultTableModel) projectTable.getModel(), selected));
    }

    private void initialize() {
        JFrame frame = new JFrame();
        frame.setSize(900, 600);
        frame.setTitle("Відділи");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JComponent panel1 = addDepartmentPanel();
        tabbedPane.addTab("Відділ", panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = addEmployeePanel();
        tabbedPane.addTab("Управління  співробітниками", panel2);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);

        JComponent panel3 = addProjectPanel();
        tabbedPane.addTab("Управління  проектами", panel3);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_3);
    }

    private JComponent addDepartmentPanel() {
        JPanel departmentPanel = new JPanel(false);
        departmentPanel.setLayout(new BorderLayout(0, 0));

        JPanel actionPanel = new JPanel(new BorderLayout());
        JPanel control = new JPanel(new BorderLayout());

        actionPanel.add(addDepartmentTable(), BorderLayout.CENTER);
        control.add(addDepartmentButton(), BorderLayout.NORTH);
        control.add(deleteDepartment());
        control.add(editDepartment(), BorderLayout.SOUTH);

        departmentPanel.add(actionPanel, BorderLayout.CENTER);
        departmentPanel.add(control, BorderLayout.EAST);

        return departmentPanel;
    }

    private JButton deleteDepartment() {

        deleteDepartmentButton = new JButton("Видалити");
        deleteDepartmentButton.addActionListener(e -> {
            int selectedDepartmentId = Integer.parseInt(departmentTable.getValueAt(departmentTable.getSelectedRow(), 0).toString());
            departmentRepository.deleteDepartment(selectedDepartmentId);
            refreshDepartmentTable();
        });
        deleteDepartmentButton.setVisible(false);

        return deleteDepartmentButton;
    }

    private JButton editDepartment() {

        editDepartmentButton = new JButton("Редагувати");
        editDepartmentButton.addActionListener(e -> {
            int selectedDepartmentId = Integer.parseInt(departmentTable.getValueAt(departmentTable.getSelectedRow(), 0).toString());
            new EditDepartmentWindow(mainWindow, departmentRepository, selectedDepartmentId);
        });
        editDepartmentButton.setVisible(false);

        return editDepartmentButton;
    }

    private JPanel addDepartmentTable() {

        JPanel tablePanel = new JPanel();
        departmentTable = new JTable();
        tablePanel.setLayout(new GridLayout());

        departmentTable.setFillsViewportHeight(true);
        departmentTable.setDefaultEditor(Object.class, null);
        departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] columnNames = {"Номер", "Назва", "Номер телефону"};
        DefaultTableModel departmentTableModel = (DefaultTableModel) departmentTable.getModel();
        departmentTableModel.setColumnIdentifiers(columnNames);

        departmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (departmentTable.getSelectedRow() != -1) {
                deleteDepartmentButton.setVisible(true);
                editDepartmentButton.setVisible(true);
            }
        });

        JScrollPane scrollPane = new JScrollPane(departmentTable);
        tablePanel.add(scrollPane);

        refreshDepartmentTable();
        return tablePanel;
    }

    private DefaultTableModel fillDepartmentTable(DefaultTableModel departmentTableModel) {
        departmentTableModel.setRowCount(0);
        List<Department> departments = departmentRepository.getDepartments();

        for (Department department : departments) {
            String[] tableRow = new String[3];
            tableRow[0] = Integer.toString(department.getDepartmentId());
            tableRow[1] = department.getName();
            tableRow[2] = department.getPhone();
            departmentTableModel.addRow(tableRow);
        }

        return departmentTableModel;
    }

    private JButton addDepartmentButton() {
        JButton addDepartmentButton = new JButton("Додати новий відділ");
        addDepartmentButton.addActionListener(e -> {
            new AddDepartmentWindow(mainWindow, departmentRepository);
        });

        return addDepartmentButton;
    }

    private JComponent addEmployeePanel() {
        JPanel employeePanel = new JPanel(false);
        employeePanel.setLayout(new BorderLayout(0, 0));

        JPanel actionPanel = new JPanel(new BorderLayout());

        actionPanel.add(addChooseDepartmentBox(), BorderLayout.EAST);
        actionPanel.add(createEmployeeTable(), BorderLayout.CENTER);
        actionPanel.add(createAddEmployeeButton(), BorderLayout.AFTER_LAST_LINE);
        actionPanel.add(createEditEmployeeButton(), BorderLayout.BEFORE_FIRST_LINE);

        employeePanel.add(actionPanel);

        return employeePanel;
    }

    private JButton createAddEmployeeButton() {
        addEmployeeButton = new JButton("Додати співробітника до відділу");
        addEmployeeButton.addActionListener(e -> {
            int selectedEmployeeId = Integer.parseInt(employeeTable.getValueAt(employeeTable.getSelectedRow(), 0).toString());
            new AddEmployeeWindow(mainWindow, positionRepository, departmentRepository, selectedEmployeeId);
        });

        addEmployeeButton.setVisible(false);

        return addEmployeeButton;
    }

    private JButton createEditEmployeeButton() {
        editEmployeeButton = new JButton("Перепризначити співробітника до відділу");
        editEmployeeButton.addActionListener(e -> {
            int selectedEmployeeId = Integer.parseInt(employeeTable.getValueAt(employeeTable.getSelectedRow(), 0).toString());
            new EditEmployeeWindow(mainWindow, positionRepository, departmentRepository, selectedEmployeeId);
        });

        editEmployeeButton.setVisible(false);

        return editEmployeeButton;
    }

    private JComboBox addChooseDepartmentBox() {

        JComboBox comboBox = new JComboBox(getDepartmentsList());

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox) e.getSource();
            String boxText = (String) box.getSelectedItem();

            Integer selected = null;
            if (!boxText.equals("")) {
                selected = Integer.parseInt(boxText);
            }
            refreshEmployeeTable(selected);
        };
        comboBox.addActionListener(actionListener);

        return comboBox;
    }

    private Object[] getDepartmentsList() {
        List<String> departments = departmentRepository.getDepartments().stream()
                                           .map(Department::getDepartmentId)
                                           .map(Objects::toString)
                                           .collect(Collectors.toList());

        departments.add("");

        return departments.toArray();
    }

    private JPanel createEmployeeTable() {
        JPanel tablePanel = new JPanel();
        employeeTable = new JTable();
        tablePanel.setLayout(new GridLayout());
        employeeTable.setFillsViewportHeight(true);
        employeeTable.setDefaultEditor(Object.class, null);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] columnNames = {"Номер", "ПІБ", "Посада", "Стать", "Дата народження", "Оклад", "Дата початку роботи", "Дата закінчення", "Відділ"};
        DefaultTableModel departmentTableModel = (DefaultTableModel) employeeTable.getModel();
        departmentTableModel.setColumnIdentifiers(columnNames);

        employeeTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();

            if (selectedRow != -1) {
                if (employeeTable.getValueAt(selectedRow, 8) != null) {
                    addEmployeeButton.setVisible(false);
                    editEmployeeButton.setVisible(true);
                } else {
                    addEmployeeButton.setVisible(true);
                    editEmployeeButton.setVisible(false);
                }
            }
        });

        employeeTable.setModel(fillEmployeeTable((DefaultTableModel) employeeTable.getModel(), null));

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }

    private DefaultTableModel fillEmployeeTable(DefaultTableModel employeeTableModel, Integer selected) {
        employeeTableModel.setRowCount(0);
        List<Employee> employees = employeeRepository.getEmployees(selected);

        for (Employee employee : employees) {
            String[] tableRow = new String[9];
            Integer employeeId = employee.getId();
            Position position = positionRepository.getPosition(employeeId);

            tableRow[0] = Integer.toString(employeeId);
            tableRow[1] = employee.getName();
            tableRow[3] = employee.getGender();
            tableRow[4] = employee.getBirthDate().toString();

            if (position != null) {
                tableRow[2] = position.getDescription();
                tableRow[5] = position.getSalary().toString();
                tableRow[6] = position.getStartDate().toString();
                Date endDate = position.getEndDate();
                if (endDate != null) {
                    tableRow[7] = endDate.toString();
                }
                tableRow[8] = String.valueOf(position.getDepartmentId());
            }

            employeeTableModel.addRow(tableRow);
        }

        return employeeTableModel;
    }

    private JComponent addProjectPanel() {
        JPanel projectPanel = new JPanel(false);
        projectPanel.setLayout(new BorderLayout(0, 0));

        JPanel actionPanel = new JPanel(new BorderLayout());

        actionPanel.add(projectTable(), BorderLayout.CENTER);
        actionPanel.add(createEditBossButton(), BorderLayout.AFTER_LAST_LINE);
        actionPanel.add(createAddBossButton(), BorderLayout.BEFORE_FIRST_LINE);
        actionPanel.add(addChooseDepartmentBoxForProjects(), BorderLayout.EAST);

        projectPanel.add(actionPanel);

        return projectPanel;
    }

    private JPanel projectTable() {
        JPanel tablePanel = new JPanel();
        projectTable = new JTable();
        tablePanel.setLayout(new GridLayout());
        projectTable.setFillsViewportHeight(true);
        projectTable.setDefaultEditor(Object.class, null);
        projectTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        String[] columnNames = {"Номер", "Клієнт", "Початок роботи", "Плановане закінчення", "Реальне закінчення", "Ціна", "Витрати", "Оцінка", "Номер відділу", "Керівник", "Номер керівника"};
        DefaultTableModel departmentTableModel = (DefaultTableModel) projectTable.getModel();
        departmentTableModel.setColumnIdentifiers(columnNames);

        projectTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = projectTable.getSelectedRow();
            if (selectedRow != -1) {
                if (projectTable.getValueAt(selectedRow, 10) != null) {
                    addBossButton.setVisible(false);
                    editBossButton.setVisible(true);
                } else {
                    editBossButton.setVisible(false);
                    addBossButton.setVisible(true);
                }
            }
        });

        projectTable.setModel(fillProjectTable((DefaultTableModel) projectTable.getModel(), null));

        JScrollPane scrollPane = new JScrollPane(projectTable);
        tablePanel.add(scrollPane);

        return tablePanel;
    }

    private DefaultTableModel fillProjectTable(DefaultTableModel projectTableModel, Integer selected) {
        projectTableModel.setRowCount(0);
        List<Project> projects = projectRepository.getProjects(selected);

        for (Project project : projects) {
            String[] tableRow = new String[11];
            int projectId = project.getId();

            tableRow[0] = Integer.toString(projectId);
            tableRow[1] = project.getClient();
            tableRow[2] = project.getStartDate().toString();
            tableRow[3] = project.getEndDatePlanned().toString();

            Date actual = project.getEndDateActual();
            if (actual != null) {
                tableRow[4] = actual.toString();
            }
            tableRow[5] = project.getPrice().toString();
            tableRow[6] = project.getExpense().toString();
            tableRow[7] = project.getEstimation();
            tableRow[8] = String.valueOf(project.getDepartmentId());

            Boss boss = bossRepository.getBoss(projectId);
            if (boss != null) {
                int employeeId = boss.getEmployeeId();
                tableRow[9] = employeeRepository.getEmployee(employeeId).getName();
                tableRow[10] = String.valueOf(employeeId);
            }

            projectTableModel.addRow(tableRow);
        }

        return projectTableModel;
    }

    private JButton createAddBossButton() {
        addBossButton = new JButton("Додати керівника до проекту");
        addBossButton.addActionListener(e -> {
            int selectedProjectId = Integer.parseInt(projectTable.getValueAt(projectTable.getSelectedRow(), 0).toString());
            int selectedDepartmentId = projectRepository.getDepartmentId(selectedProjectId);
            new AddBossWindow(mainWindow, employeeRepository, bossRepository, selectedDepartmentId, selectedProjectId);
        });

        addBossButton.setVisible(false);

        return addBossButton;
    }

    private JButton createEditBossButton() {
        editBossButton = new JButton("Перепризначити керівника");
        editBossButton.addActionListener(e -> {
            int selectedProjectId = Integer.parseInt(projectTable.getValueAt(projectTable.getSelectedRow(), 0).toString());
            int selectedDepartmentId = projectRepository.getDepartmentId(selectedProjectId);
            new EditBossWindow(mainWindow, employeeRepository, bossRepository, selectedDepartmentId, selectedProjectId);
        });

        editBossButton.setVisible(false);

        return editBossButton;
    }

    private JComboBox addChooseDepartmentBoxForProjects() {

        JComboBox comboBox = new JComboBox(getDepartmentsList());

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox) e.getSource();
            String boxText = (String) box.getSelectedItem();

            Integer selected = null;
            if (!boxText.equals("")) {
                selected = Integer.parseInt(boxText);
            }
            refreshProjectTable(selected);
        };
        comboBox.addActionListener(actionListener);

        return comboBox;
    }
}