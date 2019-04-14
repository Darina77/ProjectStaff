package domain;

import java.math.BigDecimal;
import java.util.Date;

public class Boss {

    private int id;
    private Date startDate;
    private Date endDate;
    private BigDecimal salary;
    private int employeeId;
    private int projectId;

    public Boss(int id, Date startDate, Date endDate, BigDecimal salary, int employeeId, int projectId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.employeeId = employeeId;
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
}
