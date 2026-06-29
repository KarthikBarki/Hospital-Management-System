package com.hospital.repository;

import com.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Patient Repository
 * Provides CRUD operations and custom queries for Patient entity
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPhoneNumber(String phoneNumber);
    List<Patient> findByIsAdmittedTrue();
    List<Patient> findByIsAdmittedFalse();
    @Query("SELECT p FROM Patient p WHERE LOWER(p.patientName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Patient> findByPatientNameContainingIgnoreCase(@Param("name") String name);
}