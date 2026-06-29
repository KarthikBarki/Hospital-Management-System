package com.hospital.service;

import com.hospital.entity.Appointment;
import com.hospital.entity.Patient;
import com.hospital.entity.Doctor;
import com.hospital.entity.Department;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.PatientRepository;
import com.hospital.repository.DoctorRepository;
import com.hospital.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Appointment Service
 * Business logic for Appointment operations
 */
@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Get all appointments
     */
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    /**
     * Get appointment by ID
     */
    public Optional<Appointment> getAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }

    /**
     * Get appointments for a patient
     */
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return appointmentRepository.findByPatient(patient);
    }

    /**
     * Get appointments for a doctor
     */
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return appointmentRepository.findByDoctor(doctor);
    }

    /**
     * Get appointments by status
     */
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    /**
     * Create a new appointment
     */
    public Appointment createAppointment(Appointment appointment) {
        Patient patient = patientRepository.findById(appointment.getPatient().getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Department department = departmentRepository.findById(appointment.getDepartment().getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        
        if (!doctor.getIsAvailable()) {
            throw new RuntimeException("Doctor is not available");
        }
        
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDepartment(department);
        appointment.setStatus("SCHEDULED");
        
        return appointmentRepository.save(appointment);
    }

    /**
     * Update an existing appointment
     */
    public Appointment updateAppointment(Long appointmentId, Appointment appointmentDetails) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        
        if (appointmentDetails.getAppointmentDate() != null) {
            appointment.setAppointmentDate(appointmentDetails.getAppointmentDate());
        }
        if (appointmentDetails.getReason() != null) {
            appointment.setReason(appointmentDetails.getReason());
        }
        if (appointmentDetails.getStatus() != null) {
            appointment.setStatus(appointmentDetails.getStatus());
        }
        if (appointmentDetails.getNotes() != null) {
            appointment.setNotes(appointmentDetails.getNotes());
        }
        
        return appointmentRepository.save(appointment);
    }

    /**
     * Cancel an appointment
     */
    public Appointment cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        appointment.setStatus("CANCELLED");
        return appointmentRepository.save(appointment);
    }

    /**
     * Complete an appointment
     */
    public Appointment completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
        appointment.setStatus("COMPLETED");
        return appointmentRepository.save(appointment);
    }

    /**
     * Delete an appointment
     */
    public void deleteAppointment(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new RuntimeException("Appointment not found with id: " + appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
    }
}