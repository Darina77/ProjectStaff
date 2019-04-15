package com.daryna.Models.Data;

public class Department
{
    private int id;
    private String name;
    private String phoneNumber;

    public Department(int id, String name, String phoneNumber)
    {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        else if (o.getClass() != Department.class) {
            return false;
        }
        else {
            Department dep = (Department) o;
            return this.id == dep.getId();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
