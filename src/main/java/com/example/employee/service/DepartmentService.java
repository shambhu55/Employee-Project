package com.example.employee.service;

import com.example.employee.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {
    Department saveDepartment(Department department);
    Department getDepartmentByid(long id);
    List<Department> getAllDepartment();
    boolean deleteDepartmentById(long id);
    Department updateDepartmentById(long id, Department department);
}
