package repository;

import domain.Boss;

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
        int bossId = connectionManager.getNextId("Boss", "boss_id");
        int rows = 0;
        try {
            connectionManager.setPreparedStatement(connectionManager.getConnection().prepareStatement(
                    "INSERT INTO Boss (boss_id, startDate, endDate, salary, employee_id, project_id) VALUES(" + bossId + ",?,?,?," +
                            employeeId + "," + projectId + ")"
            ));
            connectionManager.getPreparedStatement().setDate(1, Date.valueOf(LocalDate.now()));
            connectionManager.getPreparedStatement().setDate(2, null);
            connectionManager.getPreparedStatement().setBigDecimal(3, BigDecimal.ZERO);
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
            String sql = "SELECT * FROM Boss WHERE project_id= " + projectId;
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
            String sql = "UPDATE Boss SET";
            boolean updatesDefined = false;
            if (selectedProjectId != -1) {
                sql += " project_id = '" + selectedProjectId + "'";
                updatesDefined = true;
            }
            if (employeeId != -1) {
                if (updatesDefined) sql += ",";
                sql += " employee_id = '" + employeeId + "'";
                updatesDefined = true;
            }
            sql += " WHERE project_id = " + selectedProjectId;
            if (updatesDefined) {
                connectionManager.getStatement().executeUpdate(sql);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}