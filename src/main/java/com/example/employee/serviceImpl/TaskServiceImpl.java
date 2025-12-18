package com.example.employee.serviceImpl;

import com.example.employee.entity.Task;
import com.example.employee.repository.TaskRepository;
import com.example.employee.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {

        task.getEmployees().forEach(emp -> {
            if (!emp.getDepartment().getId()
                    .equals(task.getDepartment().getId())) {
                throw new RuntimeException(
                        "Employee must belong to same department as task"
                );
            }
        });

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task existing = getTaskById(id);

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
                .orElseThrow(() -> new RuntimeException("Task not found"));
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
}
