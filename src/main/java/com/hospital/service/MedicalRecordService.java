package com.hospital.service;

import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import com.hospital.repository.MedicalRecordRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Medical Record Service
 * Business logic for Medical Record operations
 */
@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    /**
     * Get all medical records
     */
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    /**
     * Get medical record by ID
     */
    public Optional<MedicalRecord> getMedicalRecordById(Long recordId) {
        return medicalRecordRepository.findById(recordId);
    }

    /**
     * Get medical records for a patient
     */
    public List<MedicalRecord> getMedicalRecordsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return medicalRecordRepository.findByPatientIdOrderByRecordDateDesc(patientId);
    }

    /**
     * Create a new medical record
     */
    public MedicalRecord createMedicalRecord(MedicalRecord record) {
        Patient patient = patientRepository.findById(record.getPatient().getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(record.getDoctor().getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setRecordDate(LocalDateTime.now());
        
        return medicalRecordRepository.save(record);
    }

    /**
     * Update an existing medical record
     */
    public MedicalRecord updateMedicalRecord(Long recordId, MedicalRecord recordDetails) {
        MedicalRecord record = medicalRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Medical record not found with id: " + recordId));
        
        if (recordDetails.getDiagnosis() != null) {
            record.setDiagnosis(recordDetails.getDiagnosis());
        }
        if (recordDetails.getTreatment() != null) {
            record.setTreatment(recordDetails.getTreatment());
        }
        if (recordDetails.getPrescription() != null) {
            record.setPrescription(recordDetails.getPrescription());
        }
        if (recordDetails.getTestResults() != null) {
            record.setTestResults(recordDetails.getTestResults());
        }
        if (recordDetails.getNotes() != null) {
            record.setNotes(recordDetails.getNotes());
        }
        
        return medicalRecordRepository.save(record);
    }

    /**
     * Delete a medical record
     */
    public void deleteMedicalRecord(Long recordId) {
        if (!medicalRecordRepository.existsById(recordId)) {
            throw new RuntimeException("Medical record not found with id: " + recordId);
        }
        medicalRecordRepository.deleteById(recordId);
    }
}