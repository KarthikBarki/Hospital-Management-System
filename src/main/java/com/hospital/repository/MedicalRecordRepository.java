package com.hospital.repository;

import com.hospital.entity.MedicalRecord;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Medical Record Repository
 * Provides CRUD operations and custom queries for MedicalRecord entity
 */
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    List<MedicalRecord> findByPatient(Patient patient);
    List<MedicalRecord> findByDoctor(Doctor doctor);
    List<MedicalRecord> findByRecordDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT m FROM MedicalRecord m WHERE m.patient.patientId = :patientId ORDER BY m.recordDate DESC")
    List<MedicalRecord> findByPatientIdOrderByRecordDateDesc(@Param("patientId") Long patientId);
}