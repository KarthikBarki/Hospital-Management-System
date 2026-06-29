package com.hospital.service;

import com.hospital.entity.Doctor;
import com.hospital.entity.Department;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Doctor Service
 * Business logic for Doctor operations
 */
@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Get all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    /**
     * Get doctor by ID
     */
    public Optional<Doctor> getDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId);
    }

    /**
     * Get doctors by specialization
     */
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    /**
     * Get doctors by department
     */
    public List<Doctor> getDoctorsByDepartmentId(Long departmentId) {
        return doctorRepository.findByDepartmentId(departmentId);
    }

    /**
     * Get available doctors
     */
    public List<Doctor> getAvailableDoctors() {
        return doctorRepository.findByIsAvailableTrue();
    }

    /**
     * Create a new doctor
     */
    public Doctor createDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            throw new RuntimeException("Doctor with email " + doctor.getEmail() + " already exists");
        }
        
        Department department = departmentRepository.findById(doctor.getDepartment().getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        doctor.setDepartment(department);
        
        return doctorRepository.save(doctor);
    }

    /**
     * Update an existing doctor
     */
    public Doctor updateDoctor(Long doctorId, Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
        
        if (doctorDetails.getDoctorName() != null) {
            doctor.setDoctorName(doctorDetails.getDoctorName());
        }
        if (doctorDetails.getSpecialization() != null) {
            doctor.setSpecialization(doctorDetails.getSpecialization());
        }
        if (doctorDetails.getPhoneNumber() != null) {
            doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        }
        if (doctorDetails.getQualification() != null) {
            doctor.setQualification(doctorDetails.getQualification());
        }
        if (doctorDetails.getExperienceYears() != null) {
            doctor.setExperienceYears(doctorDetails.getExperienceYears());
        }
        if (doctorDetails.getIsAvailable() != null) {
            doctor.setIsAvailable(doctorDetails.getIsAvailable());
        }
        
        return doctorRepository.save(doctor);
    }

    /**
     * Delete a doctor
     */
    public void deleteDoctor(Long doctorId) {
        if (!doctorRepository.existsById(doctorId)) {
            throw new RuntimeException("Doctor not found with id: " + doctorId);
        }
        doctorRepository.deleteById(doctorId);
    }
}