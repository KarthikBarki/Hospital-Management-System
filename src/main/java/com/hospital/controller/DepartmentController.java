package com.hospital.controller;

import com.hospital.entity.Department;
import com.hospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Department Controller
 * REST API endpoints for Department operations
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * GET: Retrieve all departments
     */
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }

    /**
     * GET: Retrieve a specific department by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        if (department.isPresent()) {
            return new ResponseEntity<>(department.get(), HttpStatus.OK);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Department not found with id: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * POST: Create a new department
     */
    @PostMapping
    public ResponseEntity<Object> createDepartment(@RequestBody Department department) {
        try {
            Department savedDepartment = departmentService.createDepartment(department);
            return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT: Update an existing department
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        try {
            Department updatedDepartment = departmentService.updateDepartment(id, departmentDetails);
            return new ResponseEntity<>(updatedDepartment, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE: Delete a department
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDepartment(@PathVariable Long id) {
        try {
            departmentService.deleteDepartment(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Department deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}