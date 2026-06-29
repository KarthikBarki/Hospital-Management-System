package com.hospital.controller;

import com.hospital.entity.Patient;
import com.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Patient Controller
 * REST API endpoints for Patient operations
 */
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * GET: Retrieve all patients
     */
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET: Retrieve a specific patient by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            return new ResponseEntity<>(patient.get(), HttpStatus.OK);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Patient not found with id: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET: Retrieve all admitted patients
     */
    @GetMapping("/admitted")
    public ResponseEntity<List<Patient>> getAdmittedPatients() {
        List<Patient> patients = patientService.getAdmittedPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET: Retrieve all discharged patients
     */
    @GetMapping("/discharged")
    public ResponseEntity<List<Patient>> getDischargedPatients() {
        List<Patient> patients = patientService.getDischargedPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * GET: Search patients by name
     */
    @GetMapping("/search")
    public ResponseEntity<List<Patient>> searchPatients(@RequestParam String name) {
        List<Patient> patients = patientService.searchPatientsByName(name);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    /**
     * POST: Create a new patient
     */
    @PostMapping
    public ResponseEntity<Object> createPatient(@RequestBody Patient patient) {
        try {
            Patient savedPatient = patientService.createPatient(patient);
            return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT: Update an existing patient
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails) {
        try {
            Patient updatedPatient = patientService.updatePatient(id, patientDetails);
            return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST: Admit a patient
     */
    @PostMapping("/{id}/admit")
    public ResponseEntity<Object> admitPatient(@PathVariable Long id) {
        try {
            Patient admittedPatient = patientService.admitPatient(id);
            return new ResponseEntity<>(admittedPatient, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST: Discharge a patient
     */
    @PostMapping("/{id}/discharge")
    public ResponseEntity<Object> dischargePatient(@PathVariable Long id) {
        try {
            Patient dischargedPatient = patientService.dischargePatient(id);
            return new ResponseEntity<>(dischargedPatient, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE: Delete a patient
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePatient(@PathVariable Long id) {
        try {
            patientService.deletePatient(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Patient deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}