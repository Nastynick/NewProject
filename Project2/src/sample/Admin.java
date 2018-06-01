package sample;

public class Admin extends User {
    private String adminID;

    public Admin(String userName, String passWord, String address, String email, String firstname, String lastname, int age, String phoneNumber, String adminID) {
        super(userName, passWord, address, email, firstname, lastname, age, phoneNumber);
        this.adminID = adminID;
    }

    public String getAdminID() {
        return adminID;
    }
}
