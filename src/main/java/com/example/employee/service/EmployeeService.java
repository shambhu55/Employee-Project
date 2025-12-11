package com.example.employee.service;

import com.example.employee.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    Employee getEmployeeById(Long id);
    String deleteEmployeeById(Long id);
    List<Employee> getAllEmployee();
    Employee updateEmployeeById(Long id, Employee employee);
    List<Employee> getEmployeesByDepartment(String department);
    String saveProfileImage(long id, MultipartFile file);
}
