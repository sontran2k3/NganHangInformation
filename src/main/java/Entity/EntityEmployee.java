package Entity;

import java.sql.Date;

public class EntityEmployee {
    private int employeeId;
    private String fullname;
    private String cccd;
    private Date birthday;
    private String address;
    private String phone;
    private boolean gender;
    private String branchId;
    private String role;

    public EntityEmployee(int employeeId, String fullname, String cccd, Date birthday, String address, String phone, boolean gender, String role) {
        this.employeeId = employeeId;
        this.fullname = fullname;
        this.cccd = cccd;
        this.birthday = birthday;
        this.address = address;
        this.phone = phone;
        this.gender = gender;
        this.role = role;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    private int generateRandomEmployeeId() {
        return (int) (Math.random() * 900000) + 100000; // Random 6 digits number
    }

}
