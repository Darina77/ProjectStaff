package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee {

    private int id;
    private String surname;
    private char sex;
    private Date birthday;
    private SimpleDateFormat dateFormat;

    public Employee(int id, String surname, char sex, String birthday) throws ParseException
    {
        this.id = id;
        this.surname = surname;
        this.sex = sex;
        this.dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        this.birthday = dateFormat.parse(birthday);
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
}
