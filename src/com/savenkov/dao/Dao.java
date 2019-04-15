package com.savenkov.dao;

import com.savenkov.components.DatePeriodPicker;
import com.savenkov.models.Project;
import com.savenkov.models.Saldo;
import com.savenkov.models.Transaction;

import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dao {
    public static List<Saldo> getSaldoByDatePeriodAndProject(DatePeriodPicker.DatePeriod datePeriod, Integer projectId) {
        String sql = "SELECT accounting_saldo.*, accounting_transaction.description AS description FROM accounting_saldo " +
                "JOIN accounting_transaction ON accounting_transaction.id = accounting_saldo.transaction_id " +
                "LEFT JOIN accounting_payment_from_client ON accounting_payment_from_client.transaction_id = accounting_saldo.transaction_id " +
                "LEFT JOIN accounting_payment_to_employee ON accounting_payment_to_employee.transaction_id = accounting_saldo.transaction_id " +
                "LEFT JOIN Stages ON Stages.idStage = accounting_payment_to_employee.stage_id LEFT JOIN Projects ON Projects.id = idProject OR Projects.id = accounting_payment_from_client.project_id ";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String startDate = dateFormat.format(datePeriod.start);
        final String endDate = dateFormat.format(datePeriod.end);

        sql += " WHERE timeReported BETWEEN '" + startDate + "' AND '" + endDate + "'";

        if(projectId != null) {
            if(projectId == -1) {
                sql += " AND project.id IS NULL ";
            } else {
                sql += " AND project.id = " + projectId;
            }
        }

        sql += " ORDER BY timeReported";

        System.out.println(sql);


        List<Saldo> result = new ArrayList<>();

        try {
            ResultSet rs = Db.query(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                double credit = rs.getDouble("credit");
                double debit = rs.getDouble("debit");
                double balance = rs.getDouble("balance");
                int transactionId = rs.getInt("transaction_id");
                Date timeReported = rs.getDate("timeReported");
                String description = rs.getString("description");

                result.add(new Saldo(id, credit, debit, balance, transactionId, timeReported, description));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Transaction> getNotFinishedTransactionsByDatePeriodAndProjectId(DatePeriodPicker.DatePeriod datePeriod, Integer projectId) {
        String sql = "SELECT * FROM accounting_transaction " +
                "LEFT JOIN accounting_payment_from_client ON accounting_payment_from_client.transaction_id = accounting_transaction.id " +
                "LEFT JOIN accounting_payment_to_employee ON accounting_payment_to_employee.transaction_id = accounting_transaction.id " +
                "LEFT JOIN project ON project.id = accounting_payment_to_employee.project_id OR project.id = accounting_payment_from_client.project_id ";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String startDate = dateFormat.format(datePeriod.start);
        final String endDate = dateFormat.format(datePeriod.end);

        sql += " WHERE timeCreated BETWEEN '" + startDate + "' AND '" + endDate + "'";

        sql += " AND ";
        if(projectId != null) {
            if(projectId == -1) {
                sql += "project.id IS NULL AND ";
            } else {
                sql += "project.id = " + projectId + " AND ";
            }
        }
        sql += " timePayed IS NULL";

        sql += " ORDER BY timeCreated";

        System.out.println(sql);

        List<Transaction> result = new ArrayList<>();

        try {
            ResultSet rs = Db.query(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                double amount = rs.getDouble("amount");
                Date timeCreated = rs.getDate("timeCreated");
                Date timePayed = rs.getDate("timePayed");
                String description = rs.getString("description");

                result.add(new Transaction(id, amount, timeCreated, timePayed, description));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Project> getAllProjects() {
        String sql = "SELECT * FROM Projects";

        List<Project> result = new ArrayList<>();

        try {
            ResultSet rs = Db.query(sql);
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("nameProject");

                result.add(new Project(id, name));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void createTransaction(Transaction transaction, boolean isApproved) {
        String sql = "INSERT INTO accounting_transaction(amount, timeCreated, timePayed, description) VALUES (" +
                " " + transaction.getAmount() + ", NOW(), NULL, \"" + transaction.getDescription().replace("\"", "'") + "\" ); ";

        try {
            Db.execute(sql);
        } catch(Exception e) {
            e.printStackTrace();
        }

        if(isApproved) {
            try {
                ResultSet rs = Db.query("SELECT MAX(id) AS id FROM accounting_transaction;");
                rs.next();
                int id = rs.getInt("id");
                transaction.setId(id);
                confirmTransaction(transaction);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void confirmTransaction(Transaction transaction) {
        String sql = "UPDATE accounting_transaction SET timePayed = NOW() WHERE id = " + transaction.getId() + "; ";

        Db.execute(sql);

        List<Saldo> allSaldos = getSaldoByDatePeriodAndProject(new DatePeriodPicker.DatePeriod(), null);

        Saldo lastSaldo = allSaldos.stream().max(new Comparator<Saldo>() {
            @Override
            public int compare(Saldo o1, Saldo o2) {
                return o1.getId() - o2.getId();
            }
        }).get();

        double balance = lastSaldo.getBalance();

        double newBalance = balance + transaction.getAmount();

        double debit = transaction.getAmount() > 0 ? transaction.getAmount() : 0;
        double credit = transaction.getAmount() < 0 ? -transaction.getAmount() : 0;

        sql = "INSERT INTO accounting_saldo(debit, credit, balance, transaction_id, timeReported) VALUES " +
                String.format("(%f, %f, %f, %d, NOW());", debit, credit, newBalance, transaction.getId());

        System.out.println(sql);

        Db.execute(sql);
    }
}
