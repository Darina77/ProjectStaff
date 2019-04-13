package com.savenkov.components;

import com.savenkov.dao.Dao;
import com.savenkov.models.Project;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.function.Consumer;

public class ProjectSelector extends JPanel {

    private List<Project> projects = Dao.getAllProjects();
    {
        projects.add(0, new Project(null, "All projects"));
        projects.add(new Project(-1, "Only not related to projects"));
    }

    public ProjectSelector(Consumer<Integer> onProjectSelect) {



        JComboBox comboBox = new JComboBox(projects.toArray());
        comboBox.getModel().setSelectedItem(projects.get(0));

        comboBox.setBounds(500, 500, 100, 50);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    Project selectedProject = (Project) comboBox.getModel().getSelectedItem();
                    onProjectSelect.accept(selectedProject == null ? null : selectedProject.getId());
                }
            }
        });


        this.add(comboBox);
    }
}
