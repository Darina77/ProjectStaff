package Models;

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

    public EmployeeInfoPos(String position, String depName, String startDate, String endDate, double salary) throws ParseException {
        this.position = position;
        this.depName = depName;
        this.dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        this.startDate = dateFormat.parse(startDate);
        this.endDate = dateFormat.parse(endDate);
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
        return dateFormat.format(endDate);
    }

    public double getSalary() {
        return salary;
    }

}
