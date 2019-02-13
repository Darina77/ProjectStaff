package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeInfoHead {

    private String projectName;
    private Date startDate;
    private Date endDate;
    private double salary;
    private SimpleDateFormat dateFormat;

    public EmployeeInfoHead(String projectName, String startDate, String endDate, double salary) throws ParseException {
        this.projectName = projectName;
        this.dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        this.startDate = dateFormat.parse(startDate);
        this.endDate = dateFormat.parse(endDate);
        this.salary = salary;
    }

    public String getProjectName() {
        return projectName;
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
