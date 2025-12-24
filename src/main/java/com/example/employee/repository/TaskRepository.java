package com.example.employee.repository;

import com.example.employee.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByDepartmentId(Long departmentId);
    List<Task> findByDepartmentIdAndEmployeesId(Long departmentId, Long employeeId);
}
