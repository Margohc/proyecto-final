package model;

public class EmployeeRow {

    private final String id;
    private final String firstAndMiddleName;
    private final String lastName;

    public EmployeeRow(String id, String firstAndMiddleName, String lastName) {
        this.id = id;
        this.firstAndMiddleName = firstAndMiddleName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public String getFirstAndMiddleName() {
        return firstAndMiddleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return id + " | " + firstAndMiddleName + " | " + lastName;
    }
}
