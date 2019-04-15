package com.main;

import com.daryna.View.ProjectStaffMain;
import com.projects.ProjectFrame;
import com.rozhko.View.WorksWindow;
import com.polikarpova.ui.DepartmentWindow;
import com.kharko.Main;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class MainMenu {

    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 330;

    private static final int FRAME_WIDTH = 590;
    private static final int FRAME_HEIGHT = 360;

    private static int FX = 0;

    public static void main(String[] args) throws Exception {

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

            staff.addActionListener(e -> {
                ProjectStaffMain staffMain = new ProjectStaffMain("Projects staff");
                staffMain.setSize(FRAME_WIDTH, FRAME_HEIGHT);
                staffMain.setVisible(true);
            });
            JButton departments = new JButton("Departments management");
            departments.addActionListener(e -> {
                new DepartmentWindow();
            });

            JButton projects = new JButton("Projects management");
            projects.addActionListener(e -> {
                ProjectFrame projectFrame = new ProjectFrame("Projects management");
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
                    if(FX==0) {
                    new Thread(new Runnable() {
                        public void run()
                        {
                            FX++;
                            String[] kek = new String[0];
                            Main.main(kek);
                        }
                    }).start();
                } else {
                        Platform.runLater(new Runnable(){
                            public void run() {
                                try {
                                    Main.startOld();
                                } catch (Exception e1){
                                    System.out.println(e1);
                                }
                            }
                        });
                    }
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
