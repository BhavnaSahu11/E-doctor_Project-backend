
package Outpatient.example.Intership_Backend.ServiceTest;

import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.DTO.LoginRequest;
import Outpatient.example.Intership_Backend.DTO.RegisterUserDTo;
import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Repository.AppointmentRepository;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import Outpatient.example.Intership_Backend.Service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @InjectMocks
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testUpdateDoctorProfile_DoctorNotFound() {
        Doctor doctorDTO = new Doctor();
        when(doctorRepository.findByEmail(doctorService.getLoginEmail())).thenReturn(null);

        ResponseEntity<ApiError> response = doctorService.updateDoctorProfile(doctorDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Doctor not found or unauthorized access", response.getBody().getMessage());
    }

    @Test
    void testUpdateDoctorProfile_Success() {
        Doctor existingDoctor = new Doctor();
        existingDoctor.setEmail("doctor@gmail.com");
        doctorService.setLoginEmail("doctor@gmail.com");

        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setDoctorName("Updated Name");
        updatedDoctor.setSpeciality("Updated Speciality");

        when(doctorRepository.findByEmail(doctorService.getLoginEmail())).thenReturn(existingDoctor);

        ResponseEntity<ApiError> response = doctorService.updateDoctorProfile(updatedDoctor);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Doctor profile updated successfully", response.getBody().getMessage());
        verify(doctorRepository, times(1)).save(existingDoctor);
    }

    @Test
    void testLoginDoctor() {
        LoginRequest loginRequest = new LoginRequest("doctor@gmail.com", "password");

        doctorService.loginDoctor(loginRequest);

        assertEquals("doctor@gmail.com", doctorService.getLoginEmail());
    }

    @Test
    void testGetDoctorProfile_Success() {
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@gmail.com");

        doctorService.setLoginEmail("doctor@gmail.com");
        when(doctorRepository.findByEmail("doctor@gmail.com")).thenReturn(doctor);

        Doctor result = doctorService.getDoctorProfile();

        assertNotNull(result);
        assertEquals("doctor@gmail.com", result.getEmail());
    }

    @Test
    void testGetDoctorProfile_DoctorNotFound() {
        doctorService.setLoginEmail("nonexistent@gmail.com");
        when(doctorRepository.findByEmail("nonexistent@gmail.com")).thenReturn(null);

        Doctor result = doctorService.getDoctorProfile();

        assertNull(result);
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor());
        doctors.add(new Doctor());

        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();

        assertEquals(2, result.size());
    }

    @Test
    void testGetAppointmentsByDoctorEmail() {
        doctorService.setLoginEmail("doctor@gmail.com");
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());
        appointments.add(new Appointment());

        when(appointmentRepository.findByDoctorEmail("doctor@gmail.com")).thenReturn(appointments);

        List<Appointment> result = doctorService.getAppointmentsByDoctorEmail();

        assertEquals(2, result.size());
    }
}

