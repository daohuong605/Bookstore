package dto;

public class employee {
    private int employeeId;
    private String employeeName;
    private String sex;
    private String dob;
    private String citizenId;
    private String position;
    private String phoneNumber;

    // Constructor
    public employee(int employeeId, String employeeName, String sex, String dob, String citizenId, String position, String phoneNumber) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.sex = sex;
        this.dob = dob;
        this.citizenId = citizenId;
        this.position = position;
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter Methods
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(String citizenId) {
        this.citizenId = citizenId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
