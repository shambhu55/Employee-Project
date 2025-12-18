package com.example.employee.service;

import com.example.employee.entity.Task;

import java.util.List;

public interface TaskService {

    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    Task getTaskById(Long id);
    List<Task> getAllTasks();
    void deleteTask(Long id);

    List<Task> getTasksByDepartment(Long departmentId);
    List<Task> getTasksByEmployeeAndDepartment(Long departmentId, Long employeeId);
}
