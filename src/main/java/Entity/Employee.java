package Entity;

public class Employee {
    private final String id;
    private final String firstName;
    private final String lastName;
    private final String gender;
    private final String phone;
    private final String position;
    private final String dateMember;

    public Employee(String id, String firstName, String lastName, String gender, String phone, String position, String dateMember) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.position = position;
        this.dateMember = dateMember;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getPosition() {
        return position;
    }

    public String getDateMember() {
        return dateMember;
    }
}
