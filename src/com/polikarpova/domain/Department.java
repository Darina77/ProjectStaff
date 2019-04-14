package domain;

public class Department {

    private int departmentId;
    private String name;
    private String phone;

    public Department(int id, String name, String phone) {
        this.departmentId = id;
        this.name = name;
        this.phone = phone;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int id) {
        this.departmentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
