package com.example.employee.serviceImpl;

import com.example.employee.entity.Employee;
import com.example.employee.exception.DuplicateResourceException;
import com.example.employee.exception.InvalidFileException;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;
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

//    @Override
//    public Employee saveEmployee(Employee employee) {
//        if(employeeRepository.existsByName(employee.getName())){
//            throw new DuplicateResourceException("Employee Exists with name "+employee.getName());
//        }
//        return employeeRepository.save(employee);
//    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if(employeeRepository.existsByNameAndDepartment(employee.getName(), employee.getDepartment())){
            throw new DuplicateResourceException("Employee Exists with Name "+employee.getName()+" and Department "+employee.getDepartment());
        }
        else if(employeeRepository.existsByEmail(employee.getEmail())){
            throw new DuplicateResourceException(("Employee with '"+employee.getEmail()+"' Already exist."));
        }
        else if (employeeRepository.existsByMobileNumber(employee.getMobileNumber())) {
            throw new DuplicateResourceException("Employee Mobile mobile should not be Duplicate");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @Override
    public String deleteEmployeeById(Long id) {
        if (!employeeRepository.existsById(id)){
            throw new ResourceNotFoundException("Employee not found with id "+id);
        }
        else{
            employeeRepository.deleteById(id);
            return "Employee with id "+id+" deleted Successfully !";
        }
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

//    @Override
//    public Employee updateEmployeeById(Long id, Employee employee) {
//        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
//        if(existingEmployee != null){
//            existingEmployee.setName(employee.getName());
//            existingEmployee.setDepartment(employee.getDepartment());
//            existingEmployee.setSalary(employee.getSalary());
//            return employeeRepository.save(existingEmployee);
//        }
//        else{
//            return null;
//        }
//    }

    @Override
    public Employee updateEmployeeById(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElse(null);
        if(existingEmployee == null){
            throw new ResourceNotFoundException("Employee with id "+id+" not found.");
        }
        else{
            existingEmployee.setName(employee.getName());
            existingEmployee.setDepartment(employee.getDepartment());
            existingEmployee.setSalary(employee.getSalary());
            return employeeRepository.save(existingEmployee);
        }
    }

    @Override
    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department);
    }

    @Override
    @Transactional
    public String saveProfileImage(long id, MultipartFile file) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee Not Found with Id " + id));

        List<String> allowType = List.of("image/jpg", "image/jpeg");
        if (!allowType.contains(file.getContentType()))
            throw new InvalidFileException("Only jpg or jpeg image allowed.");

        long maxSize = 2 * 1024 * 1024;
        if (file.getSize() > maxSize)
            throw new InvalidFileException("Image size must be less than 2MB.");

        String uploadDirectory = "uploads/employee-profiles/";
        File directory = new File(uploadDirectory);

        String fileName = id+"_"+employee.getName()+".jpg";
        Path filePath = Paths.get(uploadDirectory + fileName);

        try {
            Files.write(filePath, file.getBytes());
        } catch (IOException ex){
            throw new RuntimeException("Failed to Store.", ex);
        }

        employeeRepository.updateImage(id, fileName);

        return "Image Uploaded Successfully";
    }

}
