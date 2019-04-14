package com.polikarpova.repository;

import com.polikarpova.domain.Position;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PositionRepository {

    private ConnectionManager connectionManager;

    public PositionRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int createPosition(String description, BigDecimal salary, int departmentId, int employeeId) {
        int positionId = connectionManager.getNextId("Positions", "idPos");
        int rows = 0;
        try {
            connectionManager.setPreparedStatement(connectionManager.getConnection().prepareStatement(
                    "INSERT INTO Positions (endDate, idDep, idEmp, idPos, namePos, salary, startDate) VALUES(?" +
                            departmentId + "," + employeeId + "," + positionId +
                            ",?,?,?)"
            ));
            connectionManager.getPreparedStatement().setDate(4, Date.valueOf(LocalDate.now()));
            connectionManager.getPreparedStatement().setDate(1, null);
            connectionManager.getPreparedStatement().setBigDecimal(3, salary);
            connectionManager.getPreparedStatement().setString(2, description);
            rows = connectionManager.getPreparedStatement().executeUpdate();
            if (rows == 0) departmentId = 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return departmentId;
    }

    public List<Position> getPositions() {
        List<Position> positions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Positions";
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if (resultSet != null) {
                while (resultSet.next()) {
                    positions.add(new Position(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getDate(3),
                            resultSet.getDate(4),
                            resultSet.getBigDecimal(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7)));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return positions;

    }

    public Position getPosition(int employeeId) {
        Position position = null;
        try {
            String sql = "SELECT * FROM Positions WHERE idEmp = " + employeeId;
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if ((resultSet != null) && (resultSet.next())) {
                position = new Position(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getDate(4),
                        resultSet.getBigDecimal(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return position;
    }

    public void updatePosition(String description, BigDecimal salary, int selectedDepartmentId, int selectedEmployeeId) {
        try {
            String sql = "UPDATE Positions SET";
            boolean updatesDefined = false;
            if (description != null) {
                sql += " namePos = '" + description + "'";
                updatesDefined = true;
            }
            if (salary != null) {
                if (updatesDefined) sql += ",";
                sql += " salary = '" + salary + "'";
                updatesDefined = true;
            }
            if (selectedDepartmentId != -1) {
                if (updatesDefined) sql += ",";
                sql += " idDep = '" + selectedDepartmentId + "'";
                updatesDefined = true;
            }
            sql += " WHERE idEmp=" + selectedEmployeeId;
            if (updatesDefined) {
                connectionManager.getStatement().executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}