package com.savenkov.models;

import com.savenkov.components.DataTableElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Saldo implements DataTableElement {
    int id;
    double credit, debit, balance;
    int transactionId;
    String description;

    public Saldo(int id, double credit, double debit, double balance, int transactionId, Date timeReported, String description) {
        this.id = id;
        this.credit = credit;
        this.debit = debit;
        this.balance = balance;
        this.transactionId = transactionId;
        this.timeReported = timeReported;
        this.description = description;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTimeReported() {
        return timeReported;
    }

    public void setTimeReported(Date timeReported) {
        this.timeReported = timeReported;
    }

    Date timeReported;


    @Override
    public Object[] renderRaw() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String strDate = formatter.format(this.timeReported);
        return new Object[] {
                strDate,
                debit,
                credit,
                balance,
                description
        };
    }
}
