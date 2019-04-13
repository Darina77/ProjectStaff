package com.daryna.View;


import com.daryna.Controller.DbAccess;
import com.daryna.Controller.DepartmentsController;
import com.daryna.Controller.EmployeesController;

import javax.swing.*;


public class ProjectStaffMain extends JFrame {

    private static final int PANEL_WIDTH = 830;
    private static final int PANEL_HEIGHT = 530;
    private static final String db = "Projects";
    private static final String user = "admin";
    private static final String pass = "zzzz#1234";

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
