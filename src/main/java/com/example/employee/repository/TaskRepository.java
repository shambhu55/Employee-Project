package com.example.employee.repository;

import com.example.employee.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDepartmentId(Long departmentId);
    List<Task> findByDepartmentIdAndEmployeesId(Long departmentId, Long employeeId);
}
