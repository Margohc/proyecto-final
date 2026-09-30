package model;

public class Employee {

    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private String username;
    private String password;
    private String status;

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getFirstAndMiddleName() {
        return firstName + " " + middleName;
    }

    public String getSearchName() {
        return firstName + " " + lastName;
    }

    public void makeUnique(String nameSuffix, String employeeId) {
        this.lastName = lastName + nameSuffix;
        this.username = username + nameSuffix.toLowerCase();
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return firstName + " " + middleName + " " + lastName;
    }
}
