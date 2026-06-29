package com.hospital.controller;

import com.hospital.entity.MedicalRecord;
import com.hospital.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Medical Record Controller
 * REST API endpoints for Medical Record operations
 */
@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * GET: Retrieve all medical records
     */
    @GetMapping
    public ResponseEntity<List<MedicalRecord>> getAllMedicalRecords() {
        List<MedicalRecord> records = medicalRecordService.getAllMedicalRecords();
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    /**
     * GET: Retrieve a specific medical record by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getMedicalRecordById(@PathVariable Long id) {
        Optional<MedicalRecord> record = medicalRecordService.getMedicalRecordById(id);
        if (record.isPresent()) {
            return new ResponseEntity<>(record.get(), HttpStatus.OK);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Medical record not found with id: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET: Retrieve medical records for a patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPatient(@PathVariable Long patientId) {
        List<MedicalRecord> records = medicalRecordService.getMedicalRecordsByPatient(patientId);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    /**
     * POST: Create a new medical record
     */
    @PostMapping
    public ResponseEntity<Object> createMedicalRecord(@RequestBody MedicalRecord record) {
        try {
            MedicalRecord savedRecord = medicalRecordService.createMedicalRecord(record);
            return new ResponseEntity<>(savedRecord, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT: Update an existing medical record
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateMedicalRecord(@PathVariable Long id, @RequestBody MedicalRecord recordDetails) {
        try {
            MedicalRecord updatedRecord = medicalRecordService.updateMedicalRecord(id, recordDetails);
            return new ResponseEntity<>(updatedRecord, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE: Delete a medical record
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteMedicalRecord(@PathVariable Long id) {
        try {
            medicalRecordService.deleteMedicalRecord(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Medical record deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}