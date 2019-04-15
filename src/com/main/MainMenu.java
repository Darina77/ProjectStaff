package com.main;

import com.daryna.View.ProjectStaffMain;
import com.projects.ProjectFrame;
import com.rozhko.View.WorksWindow;
import com.polikarpova.ui.DepartmentWindow;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class MainMenu {

    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 330;

    private static final int FRAME_WIDTH = 590;
    private static final int FRAME_HEIGHT = 360;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(0,1));

            JLabel header = new JLabel("Projects company",  CENTER);
            header.setFont(new Font("Arial", Font.BOLD, 18));

            JLabel tittle = new JLabel("Choose:");
            panel.add(header);
            panel.add(tittle);

            JButton staff = new JButton("Staff management");
            ProjectStaffMain staffMain = new ProjectStaffMain("Projects staff");
            staff.addActionListener(e -> {
                staffMain.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                staffMain.setVisible(true);
            });
            JButton departments = new JButton("Departments management");
            departments.addActionListener(e -> {
                new DepartmentWindow();
            });
            ProjectFrame projectFrame = new ProjectFrame("Projects management");
            JButton projects = new JButton("Projects management");
            projects.addActionListener(e -> {
                projectFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                projectFrame.setVisible(true);
            });
            JButton works = new JButton("Works management");
            works.addActionListener(e -> {
            	WorksWindow window = new WorksWindow();
            	window.frame.setVisible(true);
            });
            JButton accounting = new JButton("Accounting");
            accounting.addActionListener(e -> {
                //TODO add accounting
            });
            JButton salary = new JButton("Salary management");
            salary.addActionListener(e -> {
                //TODO add salary
            });

            panel.add(staff);
            panel.add(departments);
            panel.add(projects);
            panel.add(works);
            panel.add(accounting);
            panel.add(salary);

            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(PANEL_WIDTH, PANEL_HEIGHT);
            frame.setVisible(true);
        });
    }

}
