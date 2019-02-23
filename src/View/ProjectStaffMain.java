package View;


import Controller.DbAccess;
import Controller.DepartmentsController;
import Controller.EmployeesController;

import javax.swing.*;


public class ProjectStaffMain extends JFrame {

    private static final int PANEL_WIDTH = 830;
    private static final int PANEL_HEIGHT = 530;
    private static final String db = "projects";
    private static final String user = "root";
    private static final String pass = "12345";

    public ProjectStaffMain(String title)
    {
        super(title);
        JTabbedPane panels = new JTabbedPane();
        DbAccess dbAccess = new DbAccess(db, user, pass);
        DepartmentsController depController = new DepartmentsController(dbAccess);
        JPanel departmentsPanel = new DepartmentsPanel(PANEL_WIDTH, PANEL_HEIGHT, depController);
        EmployeesController emplController = new EmployeesController(dbAccess);
        JPanel employeesPanel = new EmployeesPanel(PANEL_WIDTH, PANEL_HEIGHT, emplController);

        panels.add("Departments", departmentsPanel);
        panels.add("Employees", employeesPanel);

        add(panels);
    }
}
