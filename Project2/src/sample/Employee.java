package sample;

import sample.Model.User;

public class Employee extends User {

    private int employeeNumber;
    private double salary;

    public Employee(String username, String password, int age, String address, String email, String firstName, String surName, String gender, String phoneNumber, int employeeNumber, double salary) {
        super(username, password, age, address, email, firstName, surName, gender, phoneNumber);
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
