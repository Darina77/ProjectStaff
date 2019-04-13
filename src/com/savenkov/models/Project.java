package com.savenkov.models;

public class Project {
    Integer id;
    String name;

    public Project(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {

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

    @Override
    public String toString() {
        return name;
    }
}
