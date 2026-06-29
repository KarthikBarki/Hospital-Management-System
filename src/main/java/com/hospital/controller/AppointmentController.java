package com.hospital.controller;

import com.hospital.entity.Appointment;
import com.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Appointment Controller
 * REST API endpoints for Appointment operations
 */
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * GET: Retrieve all appointments
     */
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     * GET: Retrieve a specific appointment by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            return new ResponseEntity<>(appointment.get(), HttpStatus.OK);
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Appointment not found with id: " + id);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * GET: Retrieve appointments for a patient
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     * GET: Retrieve appointments for a doctor
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     * GET: Retrieve appointments by status
     */
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentsByStatus(@PathVariable String status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    /**
     * POST: Create a new appointment
     */
    @PostMapping
    public ResponseEntity<Object> createAppointment(@RequestBody Appointment appointment) {
        try {
            Appointment savedAppointment = appointmentService.createAppointment(appointment);
            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * PUT: Update an existing appointment
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        try {
            Appointment updatedAppointment = appointmentService.updateAppointment(id, appointmentDetails);
            return new ResponseEntity<>(updatedAppointment, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST: Cancel an appointment
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Object> cancelAppointment(@PathVariable Long id) {
        try {
            Appointment cancelledAppointment = appointmentService.cancelAppointment(id);
            return new ResponseEntity<>(cancelledAppointment, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST: Complete an appointment
     */
    @PostMapping("/{id}/complete")
    public ResponseEntity<Object> completeAppointment(@PathVariable Long id) {
        try {
            Appointment completedAppointment = appointmentService.completeAppointment(id);
            return new ResponseEntity<>(completedAppointment, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE: Delete an appointment
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteAppointment(@PathVariable Long id) {
        try {
            appointmentService.deleteAppointment(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Appointment deleted successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}