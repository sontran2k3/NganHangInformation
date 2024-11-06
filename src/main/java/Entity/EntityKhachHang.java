package Entity;

import java.math.BigDecimal;
import java.sql.Date;

public class EntityKhachHang {

    private int customerId;
    private String fullname;
    private String cccd;
    private Date birthday;
    private String address;
    private String phone;
    private String pin;
    private String email;
    private String occupation;
    private BigDecimal balance;
    private String gender; // Chuyển từ int sang String

    // Getters và Setters cho các thuộc tính
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getGender() { return gender; } // Getter cho gender
    public void setGender(int genderCode) {
        this.gender = (genderCode == 1) ? "Nam" : "Nữ";
    }
}
