package com.polikarpova.repository;

import com.polikarpova.domain.Boss;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BossRepository {

    private ConnectionManager connectionManager;

    public BossRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public int addBoss(int projectId, int employeeId) {
        int bossId = connectionManager.getNextId("Heads", "idHead");
        int rows = 0;
        try {
            connectionManager.setPreparedStatement(connectionManager.getConnection().prepareStatement(
                    "INSERT INTO Heads (endDate, idEmp, idHead, idProject, salary, startDate) VALUES(?," + employeeId + "," +
                            bossId + "," + projectId + ",?,?)"
            ));
            connectionManager.getPreparedStatement().setDate(3, Date.valueOf(LocalDate.now()));
            connectionManager.getPreparedStatement().setDate(1, Date.valueOf(LocalDate.now()));
            connectionManager.getPreparedStatement().setBigDecimal(2, BigDecimal.ZERO);
            rows = connectionManager.getPreparedStatement().executeUpdate();
            if (rows == 0) bossId = 0;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bossId;
    }

    public Boss getBoss(int projectId) {
        Boss boss = null;
        try {
            String sql = "SELECT * FROM Heads WHERE idProject= " + projectId;
            connectionManager.getStatement().execute(sql);
            ResultSet resultSet = connectionManager.getStatement().getResultSet();
            if ((resultSet != null) && (resultSet.next())) {
                boss = new Boss(
                        resultSet.getInt(1),
                        resultSet.getDate(2),
                        resultSet.getDate(3),
                        resultSet.getBigDecimal(4),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return boss;
    }

    public void updateBoss(int selectedProjectId, int employeeId) {
        try {
            String sql = "UPDATE Heads SET";
            boolean updatesDefined = false;
            if (selectedProjectId != -1) {
                sql += " idProject = '" + selectedProjectId + "'";
                updatesDefined = true;
            }
            if (employeeId != -1) {
                if (updatesDefined) sql += ",";
                sql += " idEmp = '" + employeeId + "'";
                updatesDefined = true;
            }
            sql += " WHERE idProject = " + selectedProjectId;
            if (updatesDefined) {
                connectionManager.getStatement().executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}