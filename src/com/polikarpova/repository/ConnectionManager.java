package repository;

import java.sql.*;

public class ConnectionManager {

    private Connection connection = null;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    public ConnectionManager(){
        try  {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(Exception ex) {
            System.out.println("DbAccess> " + ex.getMessage());
        }
    }

    public boolean connect(String db, String user, String passwd) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?useSSL=false&allowPublicKeyRetrieval=true", user, passwd);
            statement = connection.createStatement();
            return true;
        } catch (SQLException e) {
            System.out.println("connect> " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            System.out.println("disconnect> " + e.getMessage());
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
