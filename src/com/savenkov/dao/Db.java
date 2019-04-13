package com.savenkov.dao;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.stream.Stream;

public class Db {
    private Connection conn;


    private static String readFile(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public Db(JFrame frame) {
        String hostname = "", username, password;
        try {
            hostname = readFile("./hostname");
            username = readFile("./username");
            password = readFile("./password");

            System.out.println(hostname);
            System.out.println(username);
            System.out.println(password);

            if(hostname.length() == 0) {
                hostname = "jdbc:mysql://localhost:3306/procenko_proekt?useUnicode=yes&characterEncoding=UTF-8";
//                hostname = "jdbc:mysql://procenko-proekt.cryh31oso2ii.eu-central-1.rds.amazonaws.com:3306/procenko_proekt";
//                username = "admin";
//                password = "zzzz#1234";
            }

            if(username.length() == 0) {
                username = "root";
            }

            if(password.length() == 0) {
                password = "zzzz1234";
            }

            this.conn = DriverManager.getConnection(
                    hostname,
                    username,
                    password);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(frame, "DB is not running at " + hostname);
            e.printStackTrace();
        }

    }

    public static Db db;

    public static ResultSet query(String sql) {
        try {
            return db.conn.createStatement().executeQuery(sql);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean execute(String sql) {
        try {
            return db.conn.createStatement().execute(sql);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
