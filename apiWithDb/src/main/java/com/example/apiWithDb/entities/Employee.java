package com.example.apiWithDb.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employeeInfo")
public class Employee
{
    @Id
    private String employeeId;
    private String employeeName;
    private String employeeSecName;
    private String employeePhoto;

    public Employee() {
    }

    public Employee(String employeePhoto,
                    String employeeSecName,
                    String employeeName,
                    String employeeId) {
            this.employeePhoto = employeePhoto;
            this.employeeSecName = employeeSecName;
            this.employeeName = employeeName;
            this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeePhoto() {
        return employeePhoto;
    }

    public void setEmployeePhoto(String employeePhoto) {
        this.employeePhoto = employeePhoto;
    }

    public String getEmployeeSecName() {
        return employeeSecName;
    }

    public void setEmployeeSecName(String employeeSecName) {
        this.employeeSecName = employeeSecName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
