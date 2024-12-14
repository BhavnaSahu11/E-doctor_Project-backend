package Outpatient.example.Intership_Backend.ServiceTest;

import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.DTO.LoginRequest;
import Outpatient.example.Intership_Backend.DTO.RegisterUserDTo;
import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Entity.Patient;
import Outpatient.example.Intership_Backend.Repository.AppointmentRepository;
import Outpatient.example.Intership_Backend.Repository.PatientRepository;

import Outpatient.example.Intership_Backend.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @InjectMocks
    private PatientService patientService;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePatient() {
        // Arrange
        RegisterUserDTo registerUserDTo = new RegisterUserDTo();
        registerUserDTo.setEmail("patient@example.com");

        // Act
        patientService.createPatient(registerUserDTo);

        // Assert
        assertEquals("patient@example.com", patientService.getRegisterEmail());
        verify(patientRepository, times(1)).save(any(Patient.class));
    }

    @Test
    void testLoginPatient() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("patient@example.com");

        // Act
        patientService.loginPatient(loginRequest);

        // Assert
        assertEquals("patient@example.com", patientService.getLoginEmail());
    }

    @Test
    void testGetPatientProfile() {
        // Arrange
        Patient patient = new Patient();
        patient.setEmail("patient@example.com");
        patientService.setLoginEmail("patient@example.com");
        when(patientRepository.findByEmail("patient@example.com")).thenReturn(patient);

        // Act
        Patient result = patientService.getPatientProfile();

        // Assert
        assertNotNull(result);
        assertEquals("patient@example.com", result.getEmail());
        verify(patientRepository, times(1)).findByEmail("patient@example.com");
    }

    @Test
    void testGetAllDoctors() {
        // Arrange
        List<Patient> patients = new ArrayList<>();
        patients.add(new Patient());
        patients.add(new Patient());
        when(patientRepository.findAll()).thenReturn(patients);

        // Act
        List<Patient> result = patientService.getAllDoctors();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void testUpdateDoctorProfile_Success() {
        // Arrange
        Patient existingPatient = new Patient();
        existingPatient.setEmail("patient@example.com");
        patientService.setLoginEmail("patient@example.com");

        Patient patientDto = new Patient();
        patientDto.setPatientName("John Doe");
        patientDto.setAge(30);

        when(patientRepository.findByEmail("patient@example.com")).thenReturn(existingPatient);

        // Act
        ResponseEntity<ApiError> response = patientService.updateDoctorProfile(patientDto);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Patient profile updated successfully", response.getBody().getMessage());
        verify(patientRepository, times(1)).save(existingPatient);
    }

    @Test
    void testUpdateDoctorProfile_PatientNotFound() {
        // Arrange
        patientService.setLoginEmail("unknown@example.com");
        Patient patientDto = new Patient();
        when(patientRepository.findByEmail("unknown@example.com")).thenReturn(null);

        // Act
        ResponseEntity<ApiError> response = patientService.updateDoctorProfile(patientDto);

        // Assert
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Patient not found ", response.getBody().getMessage());
        verify(patientRepository, never()).save(any());
    }

    @Test
    void testGetAppointmentsByPatientEmail() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());
        appointments.add(new Appointment());
        patientService.setLoginEmail("patient@example.com");
        when(appointmentRepository.findByPatientEmail("patient@example.com")).thenReturn(appointments);

        // Act
        List<Appointment> result = patientService.getAppointmentsByPatientEmail();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findByPatientEmail("patient@example.com");
    }
}

