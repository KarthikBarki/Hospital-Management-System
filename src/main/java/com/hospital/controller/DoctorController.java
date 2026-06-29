package com.hospital.controller;

import com.hospital.entity.Doctor;
import com.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Doctor Controller
 * REST API endpoints for Doctor operations
 */
@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    /**
     * GET: Retrieve all doctors
     */
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * GET: Retrieve a specific doctor by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getDoctorById(@PathVariable Long id) {
        Optional<Doctor> doctor = doctorService.getDoctorById(id);
        if (doctor.isPresent()) {
            return new ResponseEntity<>(doctor.get(), HttpStatus.OK);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Doctor not found with id: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET: Retrieve doctors by specialization
     */
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<Doctor>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * GET: Retrieve doctors by department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Doctor>> getDoctorsByDepartment(@PathVariable Long departmentId) {
        List<Doctor> doctors = doctorService.getDoctorsByDepartmentId(departmentId);
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * GET: Retrieve available doctors
     */
    @GetMapping("/available")
    public ResponseEntity<List<Doctor>> getAvailableDoctors() {
        List<Doctor> doctors = doctorService.getAvailableDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    /**
     * POST: Create a new doctor
     */
    @PostMapping
    public ResponseEntity<Object> createDoctor(@RequestBody Doctor doctor) {
        try {
            Doctor savedDoctor = doctorService.createDoctor(doctor);
            return new ResponseEntity<>(savedDoctor, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT: Update an existing doctor
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDoctor(@PathVariable Long id, @RequestBody Doctor doctorDetails) {
        try {
            Doctor updatedDoctor = doctorService.updateDoctor(id, doctorDetails);
            return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE: Delete a doctor
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDoctor(@PathVariable Long id) {
        try {
            doctorService.deleteDoctor(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Doctor deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}