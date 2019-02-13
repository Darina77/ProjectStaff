package View;


import javax.swing.*;


public class ProjectStaffMain extends JFrame {

    private static final int PANEL_WIDTH = 830;
    private static final int PANEL_HEIGHT = 530;

    public ProjectStaffMain(String title)
    {
        super(title);
        JTabbedPane panels = new JTabbedPane();

        JPanel departmentsPanel = new DepartmentsPanel(PANEL_WIDTH, PANEL_HEIGHT);
        JPanel employeesPanel = new EmployeesPanel(PANEL_WIDTH, PANEL_HEIGHT);

        panels.add("Departments", departmentsPanel);
        panels.add("Employees", employeesPanel);

        add(panels);
    }
}
