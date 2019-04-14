package com.kharko.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.kharko.types.Head;
import com.kharko.types.Salary;
import com.kharko.types.Worker;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.sql.DriverManager.getConnection;

public class EmployeeDataAcessor {

    private Connection connection ;

    public EmployeeDataAcessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        //Class.forName(driverClassName);
        connection = getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ObservableList<Worker> getPersonList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet res = stmnt.executeQuery("select * from employees");
        ){
            ObservableList<Worker> positionList = FXCollections.observableArrayList();

            while (res.next()) {
                String id = res.getString("idEmp");
                String name= res.getString("surname");
                String idPos = res.getString("idPos");
                Worker position = new Worker(id, name, idPos);
                positionList.add(position);
            }

            return positionList;
        }
    }

    public ObservableList<Head> getHeadList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet res = stmnt.executeQuery("select * from heads");
        ){
            ObservableList<Head> positionList = FXCollections.observableArrayList();

            while (res.next()) {
                String id = res.getString("idEmp");
                String salary = res.getString("salary");
                String idEmp = res.getString("idEmp");
                String name = "0";
                Statement stmntget = connection.createStatement();
                ResultSet resget = stmntget.executeQuery("select * from employees where idEmp = " + idEmp);
                while (resget.next()) {
                    name =  resget.getString("surname");
                }
                Head position = new Head(id,name, salary, idEmp);
                positionList.add(position);
            }

            return positionList;
        }
    }

    public void pay(Worker employee) throws SQLException {
        Statement stmntget = connection.createStatement();
        ResultSet res = stmntget.executeQuery("select * from positions where idPos = " + employee.getidPos());
        String a = "0";
        while (res.next()) {
           a =  res.getString("salary");
        }

        long millis=System.currentTimeMillis();

        PreparedStatement stmnt = connection.prepareStatement("insert  into salary (Month, idEmp,Amount) values (?,?,?)");

        stmnt.setDate(1, new java.sql.Date(millis));
        stmnt.setString(2,employee.getid());
        stmnt.setString(3, a);

        stmnt.execute();
    }

    public void payHead(Head employee) throws SQLException {
        long millis=System.currentTimeMillis();
        PreparedStatement stmnt = connection.prepareStatement("insert  into salary_head (Month, idHead,Ammount) values (?,?,?)");

        stmnt.setDate(1, new java.sql.Date(millis));
        stmnt.setString(2, employee.getid());
        stmnt.setString(3, employee.getSalary());

        stmnt.execute();
    }

    public ObservableList<Salary>  getSalary(Worker employee, String dateStart, String dateEnd) throws Exception {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(dateStart);
        Date date2 = simpleDateFormat.parse(dateEnd);

        Statement stmnt = connection.createStatement();
        ResultSet res = stmnt.executeQuery("select * from salary where idEmp = " + employee.getid());

        ObservableList<Salary> positionList = FXCollections.observableArrayList();

        while(res.next()){
            if(res.getDate("month").compareTo(date)>=0 &&res.getDate("month").compareTo(date2)<=0){
                String id = res.getString("id");
                String name= res.getString("month");
                String idPos = res.getString("amount");
                String idEmp = res.getString("idEmp");
                Salary position = new Salary(id, idPos, name,idEmp);
                positionList.add(position);
            }
        }


        return positionList;

    }

    public ObservableList<Salary>  getSalaryHead(Head employee, String dateStart, String dateEnd) throws Exception {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = simpleDateFormat.parse(dateStart);
        Date date2 = simpleDateFormat.parse(dateEnd);

        Statement stmnt = connection.createStatement();
        ResultSet res = stmnt.executeQuery("select * from salary_head where idHead = " + employee.getid());

        ObservableList<Salary> positionList = FXCollections.observableArrayList();

        while(res.next()){
            if(res.getDate("month").compareTo(date)>=0 &&res.getDate("month").compareTo(date2)<=0){
                String id = res.getString("id");
                String name= res.getString("month");
                String idPos = res.getString("ammount");
                String idEmp = res.getString("idHead");
                Salary position = new Salary(id, idPos, name,idEmp);
                positionList.add(position);
            }
        }


        return positionList;

    }

    public void getHead(Head employee) throws SQLException {


    }

}


