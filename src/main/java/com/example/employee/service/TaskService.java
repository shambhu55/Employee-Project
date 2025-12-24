package com.example.employee.service;

import com.example.employee.entity.Task;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    void deleteTask(Long id);

    List<Task> getTasksByDepartment(Long departmentId);
    List<Task> getTasksByEmployeeAndDepartment(Long departmentId, Long employeeId);

    Task uploadAssignment(Long id, MultipartFile file);
    Task uploadSubmission(Long taskId, Long emplyeeId, MultipartFile file);

    Task giveReviewAndFeedback(Long taskId, Task task);
}
