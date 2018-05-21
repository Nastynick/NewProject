package sample;

public class Admin extends Employee {
    private String adminID;

    public Admin(String username, String password, int age, String address, String email, String firstName, String surName, String gender, String phoneNumber, int employeeNumber, double salary, String adminID) {
        super(username, password, age, address, email, firstName, surName, gender, phoneNumber, employeeNumber, salary);
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }
}
