package com.example.employee.serviceImpl;

import com.example.employee.entity.Employee;
import com.example.employee.exception.DuplicateResourceException;
import com.example.employee.exception.InvalidFileException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
import com.example.employee.util.FileUploadUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // ----------- Save Employee -----------
    @Override
    public Employee saveEmployee(Employee employee) {
        if(employeeRepository.existsByName(employee.getName()))
            throw new DuplicateResourceException("Employee Exists with Name "+employee.getName());
        else if(employeeRepository.existsByEmail(employee.getEmail()))
            throw new DuplicateResourceException(("Employee with '"+employee.getEmail()+"' Already exist."));
        else if (employeeRepository.existsByMobileNumber(employee.getMobileNumber()))
            throw new DuplicateResourceException("Employee Mobile mobile should not be Duplicate");
        return employeeRepository.save(employee);
    }

    // ---------- Fetch Employee By Id ---------
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    // ---------- Delete Employee By Id ---------
    @Override
    public String deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id))
            throw new ResourceNotFoundException("Employee not found with id "+id);
        else{
            employeeRepository.deleteById(id);
            return "Employee with id "+id+" deleted Successfully !";
        }
    }

    // ---------- Fetch All Employee ----------
    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    // ---------- Update Employee Data ----------
    @Override
    public Employee updateEmployeeById(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if(existingEmployee == null)
            throw new ResourceNotFoundException("Employee with id "+id+" not found.");
        else{
            existingEmployee.setName(employee.getName());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setSalary(employee.getSalary());
            return employeeRepository.save(existingEmployee);
        }
    }

//    // ---------- Fetch Employees By Department ----------
//    @Override
//    public List<Employee> getEmployeesByDepartment(String department) {
//        return employeeRepository.findByDepartment(department);
//    }

//    // ---------- Save Profile Image ----------
//        // save image in "id_name.jpeg" format
//    @Override
//    @Transactional
//    public String saveProfileImage(long id, MultipartFile file) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with Id " + id));
//
//        List<String> allowType = List.of("image/jpg", "image/jpeg");
//        if (!allowType.contains(file.getContentType()))
//            throw new InvalidFileException("Only jpg or jpeg image allowed.");
//
//        long maxSize = 2 * 1024 * 1024;
//        if (file.getSize() > maxSize)
//            throw new InvalidFileException("Image size must be less than 2MB.");
//
//        String uploadDirectory = "uploads/employee-profiles/";
//        String fileName = id+"_"+employee.getName()+".jpg";
//        Path filePath = Paths.get(uploadDirectory + fileName);
//
//        try {
//            Files.write(filePath, file.getBytes());
//        } catch (IOException ex){
//            throw new RuntimeException("Failed to Store.", ex);
//        }
//
//        employeeRepository.updateImage(id, fileName);
//        employee.setProfileImageUrl(fileName);
//        return fileName;
//    }
//
//    // save image in URL format (using util class)
//    @Override
//    @Transactional
//    public void uploadEmployeeImage(Long id, MultipartFile file) {
//        if (!employeeRepository.existsById(id))
//            throw new ResourceNotFoundException("Employee not found");
//        if (file == null || file.isEmpty())
//            throw new InvalidFileException("File is empty");
//        if (!"image/jpeg".equals(file.getContentType()))
//            throw new InvalidFileException("Only JPG or JPEG images allowed");
//
//        String imagePath;
//        try {
//            imagePath = FileUploadUtil.saveFile(id, file);
//        } catch (IOException e) {
//            throw new RuntimeException("Image upload failed", e);
//        }
//        employeeRepository.updateImage(id, imagePath);
//    }
//
//    // ---------- Display Profile Image ----------
//    @Override
//    public byte[] showImageById(long id) {
//        Employee employee = employeeRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not Found with id "+id));
//
//        if(employee.getProfileImageUrl() == null)
//            throw new ResourceNotFoundException("No image found for employee "+id);
//
//        String uploadDirectory = "uploads/employee-profiles/";
//        Path filPath = Paths.get(uploadDirectory + employee.getProfileImageUrl());
//
//        try {
//            return Files.readAllBytes(filPath);
//        } catch (IOException ex){
//            throw new RuntimeException("Error !");
//        }
//    }


}