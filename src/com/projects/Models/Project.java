package com.projects.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Project {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStartDate() {
        return dateFormat.format(startDate);
    }

    public void setStartDate(String startDate) {
        try {
            this.startDate = dateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPlanEndDate() {
        return dateFormat.format(planEndDate);
    }

    public void setPlanEndDate(String planEndDate) {
        try {
            this.planEndDate = dateFormat.parse(planEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getRealEndDate() {
        return dateFormat.format(realEndDate);
    }

    public void setRealEndDate(String realEndDate) {
        try {
            this.realEndDate = dateFormat.parse(realEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    private String name;
    private String client;
    private Date startDate;
    private Date planEndDate;
    private Date realEndDate;
    private double price;
    private double cost;
    private String rating;
    private int departmentId;

    private SimpleDateFormat dateFormat;

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        else if (o.getClass() != Project.class) {
            return false;
        }
        else {
            Project p = (Project) o;
            return this.id == (p.getId());
        }
    }

    public Project(int id) {
        this.id = id;
    }

    public Project(int id, String name, String client, String startDate, String planEndDate, String realEndDate, double price, double cost, String rating, int departmentId) {
        this.id = id;
        this.name = name;
        this.client = client;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.startDate = dateFormat.parse(startDate);
            this.planEndDate = dateFormat.parse(planEndDate);
            this.realEndDate = dateFormat.parse(realEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.price = price;
        this.cost = cost;
        this.rating = rating;
        this.departmentId = departmentId;
    }

}
