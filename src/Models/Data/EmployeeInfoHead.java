package Models.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeInfoHead {

    private String projectName;
    private Date startDate;
    private Date endDate;
    private double salary;
    private SimpleDateFormat dateFormat;

    public EmployeeInfoHead(String projectName, String startDate, String endDate, double salary)
    {
        this.projectName = projectName;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.startDate = dateFormat.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            this.endDate = dateFormat.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
