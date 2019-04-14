package domain;

import java.math.BigDecimal;
import java.util.Date;

public class Project {

    private int id;
    private String client;
    private Date startDate;
    private Date endDatePlanned;
    private Date endDateActual;
    private BigDecimal price;
    private BigDecimal expense;
    private String estimation;
    private int departmentId;


    public Project(int id, String client, Date startDate, Date endDatePlanned, Date endDateActual, BigDecimal price, BigDecimal expense, String estimation, int departmentId) {
       this.id = id;
       this.client = client;
       this.startDate = startDate;
       this.endDateActual = endDateActual;
       this.endDatePlanned = endDatePlanned;
       this.price = price;
       this.expense = expense;
       this.estimation = estimation;
       this.departmentId = departmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDatePlanned() {
        return endDatePlanned;
    }

    public void setEndDatePlanned(Date endDatePlanned) {
        this.endDatePlanned = endDatePlanned;
    }

    public Date getEndDateActual() {
        return endDateActual;
    }

    public void setEndDateActual(Date endDateActual) {
        this.endDateActual = endDateActual;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public String getEstimation() {
        return estimation;
    }

    public void setEstimation(String estimation) {
        this.estimation = estimation;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
