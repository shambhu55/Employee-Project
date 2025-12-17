package com.example.employee.service;

import com.example.employee.entity.Department;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    Department saveDepartment(Department department);
    Department getDepartmentByid(long id);
}
