package com.savenkov.models;

import com.savenkov.components.DataTableElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction implements DataTableElement {
    int id;
    double amount;
    Date timeCreated, timePayed;
    String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Date getTimePayed() {
        return timePayed;
    }

    public void setTimePayed(Date timePayed) {
        this.timePayed = timePayed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Transaction(int id, double amount, Date timeCreated, Date timePayed, String description) {

        this.id = id;
        this.amount = amount;
        this.timeCreated = timeCreated;
        this.timePayed = timePayed;
        this.description = description;
    }

    @Override
    public Object[] renderRaw() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
        String strDate = formatter.format(this.timeCreated);
        return new Object[] {
            strDate + " (Expected)",
            this.amount > 0 ? this.amount : 0,
            this.amount < 0 ? -this.amount : 0,
            "-------------",
            this.description
        };
    }
}
