package sample;

public class Admin extends Employee {
    private String adminID;

    public Admin(String userName, String passWord, String address, String email, String firstname, String lastname, int age, String phoneNumber, String gender, int employeeNumber, double salary, String adminID) {
        super(userName, passWord, address, email, firstname, lastname, age, phoneNumber, gender, employeeNumber, salary);
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }
}
