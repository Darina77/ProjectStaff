package Models;

public class DepartmentInfo {

    private String position;
    private int id;
    private String surname;
    private double salary;

    public DepartmentInfo(String position, double salary, int id, String surname)
    {
        this.position = position;
        this.salary = salary;
        this.id = id;
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public int getId() {
        return id;
    }


    public String getSurname() {
        return surname;
    }

    public double getSalary() {
        return salary;
    }
}
