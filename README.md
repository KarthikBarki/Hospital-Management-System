# Hospital Management System - Spring Boot Application

## Project Overview

This is a comprehensive Spring Boot application for managing hospital operations including patient records, doctor management, appointment scheduling, department management, and medical record maintenance.

## Features

### Core Modules
1. **Department Management** - Create, update, and manage hospital departments
2. **Doctor Management** - Manage doctor profiles with specialization and availability
3. **Patient Management** - Register and manage patient information and medical history
4. **Appointment Scheduling** - Schedule, update, and manage patient appointments
5. **Medical Records** - Maintain patient medical records, diagnosis, and prescriptions

## Technology Stack

- **Framework**: Spring Boot 3.1.5
- **Language**: Java 17
- **Database**: MySQL 8.0
- **ORM**: Hibernate (Spring Data JPA)
- **Build Tool**: Maven
- **API**: RESTful Web Services
- **Testing**: JUnit & Spring Boot Test
- **Additional**: Lombok, Jackson, Validation

## Project Structure

```
src/main/java/com/hospital/
├── entity/
│   ├── Department.java
│   ├── Doctor.java
│   ├── Patient.java
│   ├── Appointment.java
│   └── MedicalRecord.java
├── repository/
│   ├── DepartmentRepository.java
│   ├── DoctorRepository.java
│   ├── PatientRepository.java
│   ├── AppointmentRepository.java
│   └── MedicalRecordRepository.java
├── service/
│   ├── DepartmentService.java
│   ├── DoctorService.java
│   ├── PatientService.java
│   ├── AppointmentService.java
│   └── MedicalRecordService.java
├── controller/
│   ├── DepartmentController.java
│   ├── DoctorController.java
│   ├── PatientController.java
│   ├── AppointmentController.java
│   └── MedicalRecordController.java
└── HospitalManagementApplication.java
```

## Database Configuration

### Setup Instructions

1. **Create Database**
   ```sql
   CREATE DATABASE hospital_management_db;
   USE hospital_management_db;
   ```

2. **Update application.properties**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/hospital_management_db
   spring.datasource.username=root
   spring.datasource.password=root
   ```

3. **Run Application**
   - Spring Boot will automatically create tables using JPA annotations
   - Set `spring.jpa.hibernate.ddl-auto=update` in properties

## REST API Endpoints

### Department Endpoints
- `GET /api/departments` - Get all departments
- `GET /api/departments/{id}` - Get department by ID
- `POST /api/departments` - Create new department
- `PUT /api/departments/{id}` - Update department
- `DELETE /api/departments/{id}` - Delete department

### Doctor Endpoints
- `GET /api/doctors` - Get all doctors
- `GET /api/doctors/{id}` - Get doctor by ID
- `GET /api/doctors/specialization/{specialization}` - Get doctors by specialization
- `GET /api/doctors/department/{departmentId}` - Get doctors by department
- `GET /api/doctors/available` - Get available doctors
- `POST /api/doctors` - Create new doctor
- `PUT /api/doctors/{id}` - Update doctor
- `DELETE /api/doctors/{id}` - Delete doctor

### Patient Endpoints
- `GET /api/patients` - Get all patients
- `GET /api/patients/{id}` - Get patient by ID
- `GET /api/patients/admitted` - Get admitted patients
- `GET /api/patients/discharged` - Get discharged patients
- `GET /api/patients/search?name={name}` - Search patients by name
- `POST /api/patients` - Create new patient
- `PUT /api/patients/{id}` - Update patient
- `POST /api/patients/{id}/admit` - Admit patient
- `POST /api/patients/{id}/discharge` - Discharge patient
- `DELETE /api/patients/{id}` - Delete patient

### Appointment Endpoints
- `GET /api/appointments` - Get all appointments
- `GET /api/appointments/{id}` - Get appointment by ID
- `GET /api/appointments/patient/{patientId}` - Get appointments by patient
- `GET /api/appointments/doctor/{doctorId}` - Get appointments by doctor
- `GET /api/appointments/status/{status}` - Get appointments by status
- `POST /api/appointments` - Create new appointment
- `PUT /api/appointments/{id}` - Update appointment
- `POST /api/appointments/{id}/cancel` - Cancel appointment
- `POST /api/appointments/{id}/complete` - Complete appointment
- `DELETE /api/appointments/{id}` - Delete appointment

### Medical Record Endpoints
- `GET /api/medical-records` - Get all medical records
- `GET /api/medical-records/{id}` - Get record by ID
- `GET /api/medical-records/patient/{patientId}` - Get records by patient
- `POST /api/medical-records` - Create new medical record
- `PUT /api/medical-records/{id}` - Update medical record
- `DELETE /api/medical-records/{id}` - Delete medical record

## Sample API Requests

### Create Department
```json
POST /api/departments
{
  "departmentName": "Cardiology",
  "description": "Heart and cardiovascular diseases",
  "headOfDepartment": "Dr. John Smith"
}
```

### Create Doctor
```json
POST /api/doctors
{
  "doctorName": "Dr. Jane Doe",
  "specialization": "Cardiology",
  "email": "jane@hospital.com",
  "phoneNumber": "9876543210",
  "qualification": "MBBS, MD",
  "experienceYears": 10,
  "department": {
    "departmentId": 1
  }
}
```

### Create Patient
```json
POST /api/patients
{
  "patientName": "John Doe",
  "email": "john@email.com",
  "phoneNumber": "9876543210",
  "dateOfBirth": "1990-05-15",
  "gender": "Male",
  "bloodGroup": "O+",
  "address": "123 Main Street, City",
  "emergencyContact": "9876543211"
}
```

### Create Appointment
```json
POST /api/appointments
{
  "appointmentDate": "2024-07-20T10:00:00",
  "reason": "Regular checkup",
  "patient": {
    "patientId": 1
  },
  "doctor": {
    "doctorId": 1
  },
  "department": {
    "departmentId": 1
  }
}
```

## How to Run

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Build and Run
```bash
# Clone the repository
git clone https://github.com/KarthikBarki/Hospital-Management-System.git
cd Hospital-Management-System

# Build the project
mvn clean package

# Run the application
mvn spring-boot:run

# Or using JAR file
java -jar target/hospital-management-system-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Key Concepts Implemented

### 1. Entity Relationships
- **One-to-Many**: Department → Doctors, Department → Appointments
- **Many-to-One**: Doctor → Department, Patient → Appointments
- **Bidirectional Mappings**: Lazy and Eager loading strategies

### 2. Spring Data JPA
- Custom repository methods using query methods
- @Query annotations for complex queries
- Pagination and sorting capabilities

### 3. Hibernate ORM
- Entity annotations (@Entity, @Table, @Column)
- Relationship annotations (@OneToMany, @ManyToOne, @JoinColumn)
- Lifecycle callbacks (@PreUpdate)

### 4. RESTful API Design
- Standard HTTP methods (GET, POST, PUT, DELETE)
- Proper HTTP status codes
- Exception handling with meaningful error messages
- JSON request/response handling

### 5. Service Layer Pattern
- Business logic separation from controllers
- Transaction management
- CRUD operations implementation

### 6. Validation
- Bean validation annotations
- Input validation in services
- Exception handling

## Testing

```bash
# Run tests
mvn test

# Run specific test class
mvn test -Dtest=PatientServiceTest
```

## Future Enhancements

1. **Authentication & Authorization** - JWT-based security
2. **Audit Logging** - Track all user actions
3. **Notifications** - Email/SMS for appointments
4. **Analytics** - Dashboard for hospital statistics
5. **Billing System** - Invoice generation and payment tracking
6. **Prescription Management** - Digital prescription system
7. **File Upload** - Medical reports and documents
8. **Pagination** - Handle large datasets efficiently

## Error Handling

The application implements comprehensive error handling:
- Global exception handlers
- Custom error responses
- Validation error messages
- HTTP status code management

## Contributing

Feel free to fork the project and submit pull requests with improvements.

## License

This project is licensed under the MIT License.

## Contact

For questions or suggestions, please contact the development team.

---

**Note**: This application demonstrates enterprise application development concepts using Spring Boot, Spring Data JPA, and Hibernate. It serves as a comprehensive case study for understanding RESTful API development and database management.
