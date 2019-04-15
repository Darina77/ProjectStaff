package com.projects.Controller;

import com.daryna.Controller.DbAccess;
import com.projects.Models.Project;
import com.projects.Models.Stages;
import com.rozhko.Models.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ProjectsController {

    private static String tableName = "Projects";
    private DbAccess access;

    public ProjectsController(DbAccess access)
    {
        this.access = access;
    }

    public List<Project> getAllProjects()
    {
        access.connectionDb();
        String sql = "select * from " + tableName;
        List<Project> projects = new ArrayList<>();
        ResultSet res = access.getSet(sql);
        try {
            if (res != null)
            {
                while (res.next()){
                    projects.add(new Project(res.getInt(1),
                            res.getString(2),
                            res.getString(3),
                            res.getString(4),
                            res.getString(5),
                            res.getString(6),
                            res.getDouble(7),
                            res.getDouble(8),
                            res.getString(9),
                            res.getInt(10)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        access.disConnect();
        return projects;
    }


    public boolean addProject(Project newProject)
    {
        access.connectionDb();
        String sql = "insert into " + tableName +
                " (`nameProject`, `customer`, `startDate`, `endDatePlan`, `endDateReal`, `price`, `cost`, `rating`, `idDep`) " +
                "values ('" + newProject.getName() + "', '" + newProject.getClient() + "', '" + newProject.getStartDate() + "', '"
                + newProject.getPlanEndDate() + "', '"+ newProject.getRealEndDate() + "', '"+ newProject.getPrice() +"', '"
                + newProject.getCost() +"', '"+ newProject.getRating() + "', '" + newProject.getDepartmentId() +"')";
        boolean res = access.add(sql);
        access.disConnect();
        return res;
    }

    public boolean removeProject(int projectId)
    {
        access.connectionDb();
        String sql = "delete from " + tableName + " where ( `id` = '" + projectId + "')";
        boolean res = access.update(sql);
        access.disConnect();
        return res;
    }

    public boolean updateProjects(List<Project> all)
    {
        access.connectionDb();
        boolean res = true;
        for (Project project: all)
        {
           String sql = "update " + tableName + " set "
                   + "`nameProject` = '"+ project.getName()
                   + "', `customer` = '"+ project.getClient()
                   + "', `startDate` = '"+ project.getStartDate()
                   + "', `endDatePlan` = '" + project.getPlanEndDate()
                   +"', `endDateReal` = '" + project.getRealEndDate()
                   +"', `price` = '" + project.getPrice()
                   +"', `cost` = '" + project.getCost()
                   +"', `rating` = '" + project.getRating()
                   + "', `idDep` = '" + project.getDepartmentId()
                   + "' where (`id` ='"+ project.getId() + "')";
            res = access.update(sql);
            if(!res) break;
        }
        access.disConnect();
        return res;
    }
}
