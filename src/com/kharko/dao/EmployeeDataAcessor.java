package com.kharko.dao;

import com.daryna.Models.Data.Department;
import com.kharko.types.Viddil;
import com.savenkov.models.Transaction;
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
                ResultSet res = stmnt.executeQuery("select * from Employees");
        ){
            ObservableList<Worker> positionList = FXCollections.observableArrayList();

            while (res.next()) {
                String id = res.getString("idEmp");
                String name= res.getString("surname");
                Worker position = new Worker(id, name);
                positionList.add(position);
            }

            return positionList;
        }
    }

    public ObservableList<Head> getHeadList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet res = stmnt.executeQuery("select * from Heads");
        ){
            ObservableList<Head> positionList = FXCollections.observableArrayList();

            while (res.next()) {
                String id = res.getString("idEmp");
                String salary = res.getString("salary");
                String idEmp = res.getString("idEmp");
                String name = "0";
                Statement stmntget = connection.createStatement();
                ResultSet resget = stmntget.executeQuery("select * from Employees where idEmp = " + idEmp);
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
        ResultSet res = stmntget.executeQuery("select * from Positions where idDep = " + employee.getid());
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

        com.savenkov.dao.Dao.createTransaction(new Transaction(
                -1,
                -Double.parseDouble(a),
                new Date(), new Date(), "Salary to employee: " + employee.getName()), true);

    }

    public void payViddil(Viddil employee) throws SQLException {
        Statement stmntget = connection.createStatement();
        ResultSet res = stmntget.executeQuery("select * from Positions where idDep = " + employee.getidDep());
        long millis=System.currentTimeMillis();
        while(res.next()){
            PreparedStatement stmnt = connection.prepareStatement("insert  into salary (Month, idEmp,Amount) values (?,?,?)");
            stmnt.setDate(1, new java.sql.Date(millis));
            stmnt.setString(2,res.getString("idEmp"));
            stmnt.setString(3, res.getString("salary"));

            stmnt.execute();

            com.savenkov.dao.Dao.createTransaction(new Transaction(
                    -1,
                    -Double.parseDouble(res.getString("salary")),
                    new Date(), new Date(), "Salary to viddil: " + employee.getName()), true);
        }


    }

    public void payHead(Head employee) throws SQLException {
        long millis=System.currentTimeMillis();
        PreparedStatement stmnt = connection.prepareStatement("insert  into salary_head (Month, idHead,Ammount) values (?,?,?)");

        stmnt.setDate(1, new java.sql.Date(millis));
        stmnt.setString(2, employee.getid());
        stmnt.setString(3, employee.getSalary());

        stmnt.execute();

        com.savenkov.dao.Dao.createTransaction(new Transaction(
                -1,
                -Double.parseDouble(employee.getSalary()),
                new Date(), new Date(), "Salary to head: " + employee.getName()), true);
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
                String month= res.getString("month");
                String idPos = res.getString("amount");
                String idEmp = res.getString("idEmp");
                Salary position = new Salary(id, idPos, month,idEmp, employee.getName());
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
                Salary position = new Salary(id, idPos, name,idEmp,employee.getName());
                positionList.add(position);
            }
        }


        return positionList;

    }

    public void getHead(Head employee) throws SQLException {


    }

}


