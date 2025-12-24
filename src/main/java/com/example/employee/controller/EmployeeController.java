package com.example.employee.controller;

import com.example.employee.entity.Employee;
import com.example.employee.entity.Task;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private TaskService taskService;

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


    // employees can download task
//    @GetMapping("/{id}/assignment/download")
//    public ResponseEntity<byte[]> downloadAssignment(@PathVariable Long id) {
//        Task task = taskService.getTaskById(id);        // id through task
//        if (task.getAssignmentFilePath() == null)
//            throw new RuntimeException("No assignment uploaded");
//        Path path = Paths.get(task.getAssignmentFilePath());    // task's filepath
//        try {
//            byte[] data = Files.readAllBytes(path);
//            return ResponseEntity.ok()
//                    .contentType(MediaType.parseMediaType(task.getAssignmentFileType()))
//                    .body(data);
//        } catch (Exception e) {
//            throw new RuntimeException("Unable to download assignment");
//        }
//    }

    // Download Assignment by Employee
    @GetMapping("/{id}/assignment/download")
    public ResponseEntity<Resource> downloadAssignment(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task.getAssignmentFilePath() == null)
            throw new RuntimeException("No assignment uploaded");
        try {
            Path path = Paths.get(task.getAssignmentFilePath());
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists())
                throw new RuntimeException("File not found");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(task.getAssignmentFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + task.getAssignmentFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            throw new RuntimeException("Download failed", e);
        }
    }

    // Download Submission by manager
    @GetMapping("{id}/submission/download")
    public ResponseEntity<Resource> downloadSubmission(@PathVariable Long id){
        Task task = taskService.getTaskById(id);

        if(task.getSubmissionFilePath() == null)
            throw new ResourceNotFoundException("Assignment Not Submitted.");
        Path path = Paths.get(task.getSubmissionFilePath());
        try {
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists())
                throw new ResourceNotFoundException("File not Found.");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+task.getSubmissionFileName()+"\"")
                    .body(resource);
        }catch (Exception e){
            throw new RuntimeException("Download Failed", e);
        }
    }

//    // manager can download submission ------
//    @GetMapping("/{id}/submission/download")
//    public ResponseEntity<byte[]> downloadSubmission(@PathVariable Long id) {
//        Task task = taskService.getTaskById(id);
//        if (task.getSubmissionFilePath() == null)
//            throw new RuntimeException("No submission uploaded");
//        Path path = Paths.get(task.getSubmissionFilePath());
//        if (!Files.exists(path))
//            throw new RuntimeException("File not found on server");
//        try {
//            byte[] data = Files.readAllBytes(path);
//            String contentType = Files.probeContentType(path);
//            if (contentType == null)
//                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(data);
//        } catch (IOException e) {
//            throw new RuntimeException("Unable to download assignment", e);
//        }
//    }






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
