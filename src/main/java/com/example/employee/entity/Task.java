package com.example.employee.entity;

import com.example.employee.enums.ReviewStatus;
import com.example.employee.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @NotBlank(message = "Task name is required")
    private String taskName;

    private String description;
    private String priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status=TaskStatus.PENDING;

    private LocalDate startDate;
    private LocalDate endDate;


    private LocalDate dueDate;
    private String assignmentFilePath;
    private String assignmentFileName;
    private String assignmentFileType;


    private String submissionFilePath;
    private String submissionFileName;
    private LocalDateTime submittedAt;

    private LocalDateTime reviewedAt;

    @Column(length = 1000)
    private String feedback;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReviewStatus reviewStatus=ReviewStatus.PENDING;


    @ManyToOne(fetch = FetchType.EAGER)//
    @JoinColumn(name = "department_id", nullable = false)
    @JsonIgnoreProperties({"task"})
    private Department department;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "task_employee",
//            joinColumns = @JoinColumn(name = "task_id"),
//            inverseJoinColumns = @JoinColumn(name = "employee_id")
//    )
//    @JsonIgnoreProperties({"tasks", "department"})
//    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"task", "department"})
    private List<Employee> employees = new ArrayList<>();
}
