package com.example.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Department name is required")
    @Column(nullable = false, unique = true)
    private String name;

    @NotBlank(message = "Department code is required")
    @Column(nullable = false, unique = true)
    private String departmentCode;

    private String description;

    @NotBlank(message = "Manager name is required")
    private String managerName;

    private String location;

    @NotBlank(message = "Department status is required")
    private String status;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore //@JsonIgnoreProperties("department")
    private List<Employee> employees = new ArrayList<>();


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore //@JsonIgnoreProperties("department")
    private List<Task> tasks = new ArrayList<>();
}
