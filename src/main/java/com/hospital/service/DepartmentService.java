package com.hospital.service;

import com.hospital.entity.Department;
import com.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Department Service
 * Business logic for Department operations
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Get all departments
     */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /**
     * Get department by ID
     */
    public Optional<Department> getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId);
    }

    /**
     * Create a new department
     */
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByDepartmentName(department.getDepartmentName())) {
            throw new RuntimeException("Department with name " + department.getDepartmentName() + " already exists");
        }
        return departmentRepository.save(department);
    }

    /**
     * Update an existing department
     */
    public Department updateDepartment(Long departmentId, Department departmentDetails) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
        
        if (departmentDetails.getDepartmentName() != null) {
            department.setDepartmentName(departmentDetails.getDepartmentName());
        }
        if (departmentDetails.getDescription() != null) {
            department.setDescription(departmentDetails.getDescription());
        }
        if (departmentDetails.getHeadOfDepartment() != null) {
            department.setHeadOfDepartment(departmentDetails.getHeadOfDepartment());
        }
        
        return departmentRepository.save(department);
    }

    /**
     * Delete a department
     */
    public void deleteDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new RuntimeException("Department not found with id: " + departmentId);
        }
        departmentRepository.deleteById(departmentId);
    }
}