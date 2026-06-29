package com.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application Class for Hospital Management System
 * This application manages patient records, doctors, appointments, departments,
 * and other hospital operations using Spring Boot and Spring Data JPA.
 */
@SpringBootApplication
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
        System.out.println("Hospital Management System Started Successfully!");
    }
}