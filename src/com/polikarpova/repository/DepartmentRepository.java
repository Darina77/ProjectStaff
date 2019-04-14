package com.polikarpova.repository;

import com.polikarpova.domain.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepository {

    private ConnectionManager connectionManager;

    public DepartmentRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int insertDepartment(String departmentName, String phone) {
        int departmentId = connectionManager.getNextId("Departments", "idDep");
        int rows = 0;
        try {
            connectionManager.setPreparedStatement(connectionManager.getConnection().prepareStatement(
                    "INSERT INTO Departments (idDep, nameDep, phoneDep) VALUES(" + departmentId + ",?,?)"
            ));
            connectionManager.getPreparedStatement().setString(1, departmentName);
            connectionManager.getPreparedStatement().setString(2, phone);
            rows = connectionManager.getPreparedStatement().executeUpdate();
            if (rows == 0) departmentId = 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return departmentId;
    }

    public void updateDepartment(int departmentId, String name, String phone) {
        try {
            String sql = "UPDATE Departments SET";
            boolean updatesDefined = false;
            if (name != null) {
                sql += " nameDep = '" + name + "'";
                updatesDefined = true;
            }
            if (phone != null) {
                if (updatesDefined) sql += ",";
                sql += " phoneDep = '" + phone + "'";
                updatesDefined = true;
            }
            sql += " WHERE idDep = " + departmentId;
            if (updatesDefined) {
                connectionManager.getStatement().executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDepartment(int departmentId) {
        try {
            String sql = "delete from Departments where idDep = " + departmentId;
            connectionManager.getStatement().executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Departments";
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if (resultSet != null) {
                while (resultSet.next()) {
                    departments.add(new Department(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3)));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return departments;
    }

}