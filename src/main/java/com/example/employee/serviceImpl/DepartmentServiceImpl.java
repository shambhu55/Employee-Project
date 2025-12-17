package com.example.employee.serviceImpl;

import com.example.employee.entity.Department;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public Department getDepartmentByid(long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Null"));
    }
}
