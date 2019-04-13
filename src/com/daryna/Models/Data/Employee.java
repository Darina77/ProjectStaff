package com.daryna.Models.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {

    private int id;
    private String surname;
    private char sex;
    private Date birthday;
    private SimpleDateFormat dateFormat;

    public Employee(int id, String surname, char sex, String birthday)
    {
        this.id = id;
        this.surname = surname;
        this.sex = sex;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.birthday = dateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Employee(int id)
    {
        this.id = id;
    }

    public String getBirthday() {return dateFormat.format(birthday);}

    public void setBirthday(String birthday) throws ParseException {
        this.birthday = dateFormat.parse(birthday);
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String secondName) {
        this.surname = secondName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o){
            return true;
        }
        else if (o.getClass() != Employee.class) {
            return false;
        }
        else {
            Employee emp = (Employee) o;
            return this.id == (emp.getId());
        }
    }
}
