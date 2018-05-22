package sample;



public class Employee extends User {

    private int employeeNumber;
    private double salary;

    public Employee(String userName, String passWord, String address, String email, String firstname, String lastname, int age, String phoneNumber, int employeeNumber, double salary) {
        super(userName, passWord, address, email, firstname, lastname, age, phoneNumber);
        this.employeeNumber = employeeNumber;
        this.salary = salary;
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
