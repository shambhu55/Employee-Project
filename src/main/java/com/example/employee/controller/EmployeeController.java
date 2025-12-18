package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ---------- Save Employee ---------

            //With Only JSON Bod
    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }

//            //With Multipart file (JSON + File)
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Employee createEmployeeWithImage(@Valid @RequestPart("employee") Employee employee, @RequestPart("file") MultipartFile file){
//        Employee savedEmployee = employeeService.saveEmployee(employee);
//        String imageName = employeeService.saveProfileImage(savedEmployee.getId(), file);
//        savedEmployee.setProfileImageUrl(imageName);
//        return savedEmployee;
//    }

    // ---------- Fetch Employee By Id ---------
    @GetMapping("/{id}")
    public Employee findEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id);     // image viewable
    }

    // ---------- Delete Employee By Id ---------
    @DeleteMapping("/{id}")
    public String deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return "Employee with id "+id+" deleted Successfully !";
    }

    // ---------- Fetch All Employee ----------
    @GetMapping
    public List<Employee> findAllEmployee(){
        return employeeService.getAllEmployee();
    }

    // ---------- Update Employee Data ----------
    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable Long id, @RequestBody Employee employee){
        return employeeService.updateEmployeeById(id, employee);
    }

//    // ---------- Fetch Employees By Department ----------
//    @GetMapping("/department/{department}")
//    public List<Employee> findEmployeeByDepartment(@PathVariable String department){
//        return employeeService.getEmployeesByDepartment(department);
//    }

//    // ---------- Save Profile Image ----------
//        // save image in "id_name.jpeg" format
//    @PostMapping("/{id}/upload-image")
//    public String saveProfileImage(@PathVariable long id, @RequestParam("file") MultipartFile file){
//        employeeService.saveProfileImage(id, file);
//        return "Image Uploaded Successfully";
//    }
//
//    // save image in URL format (using util class)
//    @PostMapping("/{id}/upload")
//    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        employeeService.uploadEmployeeImage(id, file);
//        return ResponseEntity.ok("Image uploaded successfully");
//    }
//
//    // ---------- Display Profile Image ----------
//    @GetMapping("/image/{id}")
//    public ResponseEntity<byte[]> showImage(@PathVariable long id){
//        byte [] imageData = employeeService.showImageById(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(imageData);
//    }

}
