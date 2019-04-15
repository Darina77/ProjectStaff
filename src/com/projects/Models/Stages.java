package com.projects.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stages {
    private int idStage;
    private int numStage;
    private Date startDate;
    private Date planEndDate;
    private Date realEndDate;
    private double price;
    private double cost;
    private int idProject;

    private SimpleDateFormat dateFormat;

    public Stages(int idStage)
    {
        this.idStage = idStage;
    }
    public Stages(int idStage, int numStage, String startDate, String planEndDate, String realEndDate,
                  double price, double cost, int idProject) {
        this.numStage = numStage;
        this.idStage = idStage;
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
        this.idProject = idProject;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        else if (o.getClass() != Stages.class) {
            return false;
        }
        else {
            Stages s = (Stages) o;
            return this.idStage == (s.getIdStage());
        }
    }
    public int getIdProject() {
        return idProject;
    }

    public void setIdProject(int idProject) {
        this.idProject = idProject;
    }

    public int getIdStage() {
        return idStage;
    }

    public void setIdStage(int idStage) {
        this.idStage = idStage;
    }

    public int getNumStage() {
        return numStage;
    }

    public void setNumStage(int numStage) {
        this.numStage = numStage;
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

    public void setPlanEndDate(String  planEndDate) {
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
}
