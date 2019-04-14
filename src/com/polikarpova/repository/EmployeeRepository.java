package com.polikarpova.repository;

import com.polikarpova.domain.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private ConnectionManager connectionManager;

    public EmployeeRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<Employee> getEmployees(Integer selected) {

        String sql = "SELECT * FROM Employees";

        if (selected != null) {
            sql = "SELECT T1.idEmp, surname, sex, birthday FROM Employees T1 "
                          + "INNER JOIN Positions T2 ON T1.idEmp = T2.idEmp WHERE T2.idDep=" + selected.intValue();
        }

        List<Employee> employees = new ArrayList<>();
        try {
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if (resultSet != null) {
                while (resultSet.next()) {
                    employees.add(new Employee(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getDate(4)));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public Employee getEmployee(int employeeId) {
        Employee employee = null;
        try {
            String sql = "SELECT * FROM Employees WHERE idEmp= " + employeeId;
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if ((resultSet != null) && (resultSet.next())) {
                employee = new Employee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }
}