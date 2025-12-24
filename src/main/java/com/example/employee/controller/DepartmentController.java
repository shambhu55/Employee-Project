package com.example.employee.controller;

import com.example.employee.entity.Department;
import com.example.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public Department createDepartment(@RequestBody Department department){
        return departmentService.saveDepartment(department);
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable long id){
        return departmentService.getDepartmentByid(id);
    }

    @GetMapping
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }

    @DeleteMapping("/{id}")
    public String deleteDepartmentById(@PathVariable long id){
        if(departmentService.deleteDepartmentById(id))
            return "Deleted";
        return "Not Deleted";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Department updateDepartment(@PathVariable long id, @RequestBody Department department){
        return departmentService.updateDepartmentById(id, department);
    }
}
