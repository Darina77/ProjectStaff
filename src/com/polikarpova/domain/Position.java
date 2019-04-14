package domain;

import java.math.BigDecimal;
import java.util.Date;

public class Position {

    private int id;
    private String description;
    private Date startDate;
    private Date endDate;
    private BigDecimal salary;
    private int departmentId;
    private int employeeId;

    public Position(int id, String description, Date startDate, Date endDate, BigDecimal salary, int departmentId, int employeeId) {
        this.description = description;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.employeeId = employeeId;
        this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public int getEmployeeId() {
        return employeeId;
    }
}
