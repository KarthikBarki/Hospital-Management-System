package com.hospital.repository;

import com.hospital.entity.Doctor;
import com.hospital.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Doctor Repository
 * Provides CRUD operations and custom queries for Doctor entity
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByDepartment(Department department);
    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByIsAvailableTrue();
    @Query("SELECT d FROM Doctor d WHERE d.department.departmentId = :departmentId")
    List<Doctor> findByDepartmentId(@Param("departmentId") Long departmentId);
}