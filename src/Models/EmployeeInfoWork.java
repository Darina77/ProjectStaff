package Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeInfoWork {

    private String description;
    private String projectName;
    private int id;
    private SimpleDateFormat dateFormat;
    private Date startDate;
    private Date endDate;

    public EmployeeInfoWork(String description, String projectName, int id, String startDate, String endDate) throws ParseException {
        this.description = description;
        this.projectName = projectName;
        this.id = id;
        this.dateFormat = new SimpleDateFormat("dd.mm.yyyy");
        this.startDate = dateFormat.parse(startDate);
        this.endDate = dateFormat.parse(endDate);
    }

    public String getDescription() {
        return description;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getId() {
        return id;
    }

    public String getStartDate() {
        return dateFormat.format(startDate);
    }

    public String getEndDate() {
        return dateFormat.format(endDate);
    }

}
