package com.example.employee.controller;

import com.example.employee.entity.Task;
import com.example.employee.enums.ReviewStatus;
import com.example.employee.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.employee.enums.ReviewStatus.NEEDS_CHANGES;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public Task create(@RequestBody @Valid Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }

    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Task deleted successfully";
    }


    @GetMapping("/department/{departmentId}")
    public List<Task> getByDepartment(@PathVariable Long departmentId) {
        return taskService.getTasksByDepartment(departmentId);
    }


    @GetMapping("/department/{departmentId}/employee/{employeeId}")
    public List<Task> getByEmployeeAndDepartment(
            @PathVariable Long departmentId,
            @PathVariable Long employeeId) {
        return taskService.getTasksByEmployeeAndDepartment(departmentId, employeeId);
    }


    // Task uploading by Manager.
    @PostMapping(
            value = "/{id}/assignment/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Task uploadAssignment(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        return taskService.uploadAssignment(id, file);
    }


    // submission by employee
    @PostMapping(
            value = "/{taskId}/submission/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public Task uploadSubmission(
            @PathVariable Long taskId,
            @RequestParam Long employeeId,
            @RequestParam("file") MultipartFile file) {
        return taskService.uploadSubmission(taskId, employeeId, file);
    }

    @PostMapping(value = "/{taskId}/reSubmission/upload")
    public Task reUploadSubmission(
            @PathVariable Long taskId,
            @RequestParam Long employeeId,
            @RequestParam("file") MultipartFile file
            ){
        Task task = taskService.getTaskById(taskId);
        if (task.getReviewStatus() != NEEDS_CHANGES)
            throw new RuntimeException("You can not Resubmit.");
        return taskService.uploadSubmission(taskId, employeeId, file);
    }


    // giveReviewAndFeedback
    @PutMapping("/{taskId}/ReviewFeedback")
    public Task giveReviewAndFeedback(@PathVariable Long taskId, @RequestBody Task task){
        return taskService.giveReviewAndFeedback(taskId, task);
    }
}

