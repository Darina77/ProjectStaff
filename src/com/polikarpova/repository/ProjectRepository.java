package repository;

import domain.Project;

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

        String sql = "SELECT * FROM Project";

        if (selected != null) {
            sql = "SELECT * FROM Project WHERE department_id=" + selected.intValue();
        }

        List<Project> projects = new ArrayList<>();
        try {
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if (resultSet != null) {
                while (resultSet.next()) {
                    projects.add(new Project(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4),
                            resultSet.getDate(5),
                            resultSet.getBigDecimal(6),
                            resultSet.getBigDecimal(7),
                            resultSet.getString(8),
                            resultSet.getInt(9)
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
            String sql = "SELECT department_id FROM Project WHERE project_id= " + projectId;
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