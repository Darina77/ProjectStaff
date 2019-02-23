package Controller;

import java.sql.*;

public class DbAccess {

    private Connection conn = null;
    private String db;
    private String user;
    private String passwd;

    public DbAccess(String db, String user, String passwd) {
        try {
            this.db = db;
            this.user = user;
            this.passwd = passwd;
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("DbAccess> " + ex.getMessage());
        }
    }

    public boolean connectionDb() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", user, passwd);
            return true;
        } catch (SQLException e) {

            System.out.println("connectionDB> " + e.getMessage());
            return false;
        }
    }

    public void disConnect() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.out.println("disConnect> " + e.getMessage());
        }
    }

    public ResultSet getSet(String sql) {
        try {
            if (conn != null) {
                Statement s = conn.createStatement();
                s.execute(sql);
                return s.getResultSet();
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean add(String sql)
    {
        try {
            conn.setAutoCommit(false);
            if (conn != null) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.executeUpdate();
                conn.setAutoCommit(true);
                return true;
            } else  {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(String sql)
    {
        try {
            conn.setAutoCommit(false);
            try{
                Statement s = conn.createStatement();
                s.execute("SET SQL_SAFE_UPDATES = 0");
                s.executeUpdate(sql);
                conn.setAutoCommit(true);
                return true;
            }
            catch (SQLException e) {
                System.out.println(" delPerson :" + e.getMessage());
            }
            conn.setAutoCommit(true);
        }
        catch (SQLException e) { System.out.println(e.getMessage());}
        return false;
    }
}
