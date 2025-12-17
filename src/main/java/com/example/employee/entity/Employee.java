package com.example.employee.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    //@NotBlank(message = "Employee Department is required.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference
    private Department department;

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
}
