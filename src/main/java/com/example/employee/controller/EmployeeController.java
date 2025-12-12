package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);     // image viewable
    }

    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return "Employee with id "+id+" deleted Successfully !";
    }

    @GetMapping
    public List<Employee> findAllEmployee(){
        return employeeService.getAllEmployee();
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Long id, @RequestBody Employee employee){
        return employeeService.updateEmployeeById(id, employee);
    }

    @GetMapping("/department/{department}")
    public List<Employee> findEmployeeByDepartment(@PathVariable String department){
        return employeeService.getEmployeesByDepartment(department);
    }

    @PostMapping("/{id}/upload-image")
    public String saveProfileImage(@PathVariable long id, @RequestParam("file") MultipartFile file){
        employeeService.saveProfileImage(id, file);
        return "Image Uploaded Successfully";
    }
}
