package com.projects;

import com.daryna.Controller.DbAccess;
import com.projects.Controller.ProjectsController;
import com.projects.Controller.StagesController;
import com.projects.View.ProjectsPanel;
import com.projects.View.StagesPanel;

import javax.swing.*;


public class ProjectFrame extends JFrame {
    private static final String db = "Projects";
    private static final String user = "root";
    private static final String pass = "123456";
    private static final int PANEL_WIDTH = 830;
    private static final int PANEL_HEIGHT = 530;


    public ProjectFrame(String title) {
        super(title);
        JTabbedPane panels = new JTabbedPane();
        DbAccess dbAccess = new DbAccess(db, user, pass);
        StagesController sController = new StagesController(dbAccess);
        JPanel sPanel = new StagesPanel(PANEL_WIDTH, PANEL_HEIGHT, sController);
        ProjectsController pController = new ProjectsController(dbAccess);
        JPanel pPanel = new ProjectsPanel(PANEL_WIDTH, PANEL_HEIGHT, pController, (StagesPanel) sPanel);



        panels.add("Projects", pPanel);
        panels.add("Stages", sPanel);


        add(panels);
    }
}
