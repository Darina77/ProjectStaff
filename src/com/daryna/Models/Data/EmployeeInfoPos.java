package com.daryna.Models.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeInfoPos {

    private String position;
    private String depName;
    private SimpleDateFormat dateFormat;
    private Date startDate;
    private Date endDate;
    private double salary;

    public EmployeeInfoPos(String position, String depName, String startDate, String endDate, double salary) {
        this.position = position;
        this.depName = depName;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.startDate = dateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (endDate != null) {
            try {
                this.endDate = dateFormat.parse(endDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public String getDepName() {
        return depName;
    }

    public String getStartDate() {
        return dateFormat.format(startDate);
    }

    public String getEndDate() {
        if (endDate != null) {
            return dateFormat.format(endDate);
        } else return  "";
    }

    public double getSalary() {
        return salary;
    }

}
