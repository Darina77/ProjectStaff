package com.polikarpova.repository;

import com.polikarpova.domain.Project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {

    private ConnectionManager connectionManager;

    public ProjectRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<Project> getProjects(Integer selected) {

        String sql = "SELECT * FROM Projects";

        if (selected != null) {
            sql = "SELECT * FROM Projects WHERE idDep=" + selected.intValue();
        }

        List<Project> projects = new ArrayList<>();
        try {
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if (resultSet != null) {
                while (resultSet.next()) {
                    projects.add(new Project(
                            resultSet.getInt(1),
                            resultSet.getString(3),
                            resultSet.getDate(4),
                            resultSet.getDate(5),
                            resultSet.getDate(6),
                            resultSet.getBigDecimal(7),
                            resultSet.getBigDecimal(8),
                            resultSet.getString(9),
                            resultSet.getInt(10)
                    ));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return projects;
    }

    public int getDepartmentId(int projectId) {
        int departmentId = 0;
        try {
            String sql = "SELECT idDep FROM Projects WHERE id= " + projectId;
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if ((resultSet != null) && (resultSet.next())) {
                departmentId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return departmentId;
    }
}