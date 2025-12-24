package com.example.employee.serviceImpl;

import com.example.employee.entity.Department;
import com.example.employee.entity.Employee;
import com.example.employee.entity.Task;
import com.example.employee.exception.ResourceNotFoundException;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.TaskRepository;
import com.example.employee.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

//    @Override
//    public Task createTask(Task task) {
//        Long taskDeptId = task.getDepartment().getId();
//        List<Employee> managedEmployees = new ArrayList<>();
//        for (Employee emp : task.getEmployees()) {
//            Employee dbEmployee = employeeRepository.findById(emp.getId())
//                    .orElseThrow(() ->
//                            new RuntimeException("Employee not found " + emp.getId()));
//            if (!dbEmployee.getDepartment().getId().equals(taskDeptId))
//                throw new RuntimeException("Employee department mismatch");
//            managedEmployees.add(dbEmployee);
//        }
//        task.setEmployees(managedEmployees);
//        return taskRepository.save(task);
//    }

    @Override
    public Task createTask(Task task) {
        Department department = departmentRepository.findById(
                task.getDepartment().getId()).orElseThrow(() -> new RuntimeException("Department not found"));
        List<Employee> assignedEmployees = new ArrayList<>();
        for (Employee emp : task.getEmployees()) {
            Employee dbEmployee = employeeRepository.findById(emp.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + emp.getId()));
            if (!dbEmployee.getDepartment().getId().equals(department.getId()))
                throw new RuntimeException("Employee department mismatch");
            if (dbEmployee.getTask() != null)
                throw new RuntimeException(
                        "Employee " + dbEmployee.getId() + " is already assigned to another task");
            dbEmployee.setTask(task);
            assignedEmployees.add(dbEmployee);
        }
        task.setDepartment(department);
        task.setEmployees(assignedEmployees);
        return taskRepository.save(task);
    }


    @Override
    public Task updateTask(Long id, Task task) {
        Task existing = getTaskById(id);
        if (existing == null)
            throw new ResourceNotFoundException("Task Not exist with Id "+id);
        existing.setTaskName(task.getTaskName());
        existing.setDescription(task.getDescription());
        existing.setPriority(task.getPriority());
        existing.setStatus(task.getStatus());
        existing.setStartDate(task.getStartDate());
        existing.setEndDate(task.getEndDate());
        existing.setEmployees(task.getEmployees());
        return taskRepository.save(existing);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found !"));
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public List<Task> getTasksByDepartment(Long departmentId) {
        return taskRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<Task> getTasksByEmployeeAndDepartment(Long departmentId, Long employeeId) {
        return taskRepository.findByDepartmentIdAndEmployeesId(departmentId, employeeId);
    }

    // upload task by manager
    @Override
    public Task uploadAssignment(Long id, MultipartFile file) {
        Task task = getTaskById(id);

        String uploadDirectory = "uploads/tasks/assignments/";
        new File(uploadDirectory).mkdirs();
        String fileName = "task_"+id+"_assignment_"+file.getOriginalFilename();
        Path path = Paths.get(uploadDirectory+fileName);
        try {
            Files.write(path, file.getBytes());
        }catch (Exception ex){
            throw new RuntimeException("Failed to upload.");
        }
        task.setAssignmentFilePath(path.toString());
        task.setAssignmentFileName(fileName);
        task.setAssignmentFileType(file.getContentType());
        return taskRepository.save(task);
    }

    // upload submission by employee
    @Override
    public Task uploadSubmission(Long taskId, Long employeeId, MultipartFile file) {
        Task task = getTaskById(taskId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new ResourceNotFoundException("Employee Not found."));       // employee exist check
        if(!employee.getDepartment().getId().equals(task.getDepartment().getId()))
            throw new RuntimeException("Employee not in same department.");
        if(!employee.getTask().getTaskId().equals(task.getTaskId()))        // employee and assigned task check
            throw new RuntimeException("Task must uploaded by task assigned employee !");

        String uploadDir = "uploads/tasks/submissions/";
        new File(uploadDir).mkdirs();
        String fileName = "task_" + taskId + "_employee_" + employeeId + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);

        try {
            Files.write(path, file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Submission upload failed");
        }
        task.setSubmittedAt(LocalDateTime.now());
        task.setSubmissionFilePath(path.toString());
        task.setSubmissionFileName(fileName);
        return taskRepository.save(task);
    }

    // Give Task Review
    @Override
    public Task giveReviewAndFeedback(Long taskId, Task task) {
        Task existingTask = getTaskById(taskId);
        if (existingTask==null)
            throw new ResourceNotFoundException("Task Not found");
        existingTask.setReviewedAt(LocalDateTime.now());
        existingTask.setFeedback(task.getFeedback());
        existingTask.setReviewStatus(task.getReviewStatus());
        return taskRepository.save(existingTask);
    }
}
