package com.example.employee.serviceImpl;

import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public boolean deleteDepartmentById(long id) {
        if (departmentRepository.existsById(id)){
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Department updateDepartmentById(long id, Department department) {
        Department existDepartment = departmentRepository.findById(id).orElse(null);
        if(existDepartment == null)
            throw new ResourceNotFoundException("Department not exist !");
        existDepartment.setName(department.getName());
        return departmentRepository.save(existDepartment);
    }


}
