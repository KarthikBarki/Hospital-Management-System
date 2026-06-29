package com.hospital.service;

import com.hospital.entity.Patient;
import com.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Patient Service
 * Business logic for Patient operations
 */
@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    /**
     * Get all patients
     */
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    /**
     * Get patient by ID
     */
    public Optional<Patient> getPatientById(Long patientId) {
        return patientRepository.findById(patientId);
    }

    /**
     * Get all admitted patients
     */
    public List<Patient> getAdmittedPatients() {
        return patientRepository.findByIsAdmittedTrue();
    }

    /**
     * Get all discharged patients
     */
    public List<Patient> getDischargedPatients() {
        return patientRepository.findByIsAdmittedFalse();
    }

    /**
     * Search patients by name
     */
    public List<Patient> searchPatientsByName(String name) {
        return patientRepository.findByPatientNameContainingIgnoreCase(name);
    }

    /**
     * Create a new patient
     */
    public Patient createPatient(Patient patient) {
        if (patient.getEmail() != null && patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new RuntimeException("Patient with email " + patient.getEmail() + " already exists");
        }
        if (patientRepository.findByPhoneNumber(patient.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Patient with phone " + patient.getPhoneNumber() + " already exists");
        }
        return patientRepository.save(patient);
    }

    /**
     * Update an existing patient
     */
    public Patient updatePatient(Long patientId, Patient patientDetails) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        
        if (patientDetails.getPatientName() != null) {
            patient.setPatientName(patientDetails.getPatientName());
        }
        if (patientDetails.getPhoneNumber() != null) {
            patient.setPhoneNumber(patientDetails.getPhoneNumber());
        }
        if (patientDetails.getAddress() != null) {
            patient.setAddress(patientDetails.getAddress());
        }
        if (patientDetails.getMedicalHistory() != null) {
            patient.setMedicalHistory(patientDetails.getMedicalHistory());
        }
        if (patientDetails.getEmergencyContact() != null) {
            patient.setEmergencyContact(patientDetails.getEmergencyContact());
        }
        if (patientDetails.getIsAdmitted() != null) {
            patient.setIsAdmitted(patientDetails.getIsAdmitted());
        }
        
        return patientRepository.save(patient);
    }

    /**
     * Admit a patient
     */
    public Patient admitPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        patient.setIsAdmitted(true);
        patient.setAdmissionDate(LocalDateTime.now());
        return patientRepository.save(patient);
    }

    /**
     * Discharge a patient
     */
    public Patient dischargePatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));
        patient.setIsAdmitted(false);
        patient.setDischargeDate(LocalDateTime.now());
        return patientRepository.save(patient);
    }

    /**
     * Delete a patient
     */
    public void deletePatient(Long patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }
        patientRepository.deleteById(patientId);
    }
}