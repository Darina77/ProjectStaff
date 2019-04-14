package com.polikarpova.repository;

import java.sql.*;

public class ConnectionManager {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public boolean connect(String db, String user, String passwd) {
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?useSSL=false&allowPublicKeyRetrieval=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, passwd);
//            statement = connection.createStatement();
//            return true;
//        } catch (SQLException e) {
//            System.out.println("connect> " + e.getMessage());
//            return false;
//        }
        try {
            connection = DriverManager.getConnection("jdbc:mysql://procenko-proekt.cryh31oso2ii.eu-central-1.rds.amazonaws.com:3306/" + db + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", user, passwd);
            statement = connection.createStatement();
            return true;
        } catch (SQLException e) {
            System.out.println("connect> " + e.getMessage());
            return false;
        }
    }

    public int getNextId(String table, String idFieldName) {
        int i = -1;
        try {
            String sql = "SELECT MAX(" + idFieldName + ") FROM " + table;
            statement.execute(sql);
            resultSet = statement.getResultSet();
            if ((resultSet != null) && (resultSet.next())) i = resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i + 1;
    }


    public Statement getStatement() {
        return statement;
    }

    public Connection getConnection(){
        return connection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }
}
