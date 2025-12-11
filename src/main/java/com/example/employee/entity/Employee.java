package com.example.employee.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name= "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Employee name is required.")
    @Size(min = 2, max = 50, message = "Name must between 2-50 characters.")
    private String name;

    @NotBlank(message = "Employee Department is required.")
    private String department;

    @Min(value = 1, message = "Salary not be zero.")
    private double salary;

    @NotBlank(message = "Employee Email is required.")
    @Email(message = "Invalid Email Format.")
    private String email;

    @Pattern(regexp = "^[1-9][0-9]{9}$", message = "Mobile Number must be 10 digits.")
    private String mobileNumber;

    private String address;

    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pin code must be 6 digits.")
    private String pincode;

    private String profileImageUrl;

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getMobileNumber() {
//        return mobileNumber;
//    }
//
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber =  mobileNumber;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getPincode() {
//        return pincode;
//    }
//
//    public void setPincode(String pincode) {
//        this.pincode = pincode;
//    }
//
//    public String getProfileImageUrl() {
//        return profileImageUrl;
//    }
//
//    public void setProfileImageUrl(String profileImageUrl) {
//        this.profileImageUrl = profileImageUrl;
//    }
}
