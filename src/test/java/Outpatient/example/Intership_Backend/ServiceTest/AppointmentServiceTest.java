package Outpatient.example.Intership_Backend.ServiceTest;

import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Entity.Patient;
import Outpatient.example.Intership_Backend.Repository.AppointmentRepository;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import Outpatient.example.Intership_Backend.Repository.PatientRepository;

import Outpatient.example.Intership_Backend.Service.AppointmentService;
import Outpatient.example.Intership_Backend.Service.DoctorService;
import Outpatient.example.Intership_Backend.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookAppointment_Success() {
        // Arrange
        String patientEmail = "patient@example.com";
        String doctorEmail = "doctor@example.com";

        Patient patient = new Patient();
        patient.setEmail(patientEmail);

        Doctor doctor = new Doctor();
        doctor.setEmail(doctorEmail);

        Appointment appointment = new Appointment();
        appointment.setDoctorEmail(doctorEmail);

        when(patientService.getLoginEmail()).thenReturn(patientEmail);
        when(patientRepository.findByEmail(patientEmail)).thenReturn(patient);
        when(doctorRepository.findByEmail(doctorEmail)).thenReturn(doctor);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        Appointment result = appointmentService.bookAppointment(appointment);

        // Assert
        assertNotNull(result);
        assertEquals(patient, result.getPatient());
        verify(patientRepository, times(1)).findByEmail(patientEmail);
        verify(doctorRepository, times(1)).findByEmail(doctorEmail);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testBookAppointment_DoctorNotFound() {
        // Arrange
        String doctorEmail = "nonexistent@doctor.com";

        Appointment appointment = new Appointment();
        appointment.setDoctorEmail(doctorEmail);

        when(doctorRepository.findByEmail(doctorEmail)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> appointmentService.bookAppointment(appointment));

        assertEquals("Doctor with email " + doctorEmail + " does not exist.", exception.getMessage());
        verify(doctorRepository, times(1)).findByEmail(doctorEmail);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }

    @Test
    void testGetAllAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(new Appointment());
        appointments.add(new Appointment());
        when(appointmentRepository.findAll()).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.getAllAppointments();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAppointmentById_Found() {
        // Arrange
        int appointmentId = 1;
        Appointment appointment = new Appointment();
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        // Act
        Optional<Appointment> result = appointmentService.getAppointmentById(appointmentId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(appointment, result.get());
        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void testGetAppointmentById_NotFound() {
        // Arrange
        int appointmentId = 999;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act
        Optional<Appointment> result = appointmentService.getAppointmentById(appointmentId);

        // Assert
        assertFalse(result.isPresent());
        verify(appointmentRepository, times(1)).findById(appointmentId);
    }

    @Test
    void testCancelAppointment_Success() {
        // Arrange
        int appointmentId = 1;
        Appointment appointment = new Appointment();
        appointment.setStatus("Scheduled");

        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        // Act
        appointmentService.cancelAppointment(appointmentId);

        // Assert
        assertEquals("Cancelled", appointment.getStatus());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testCancelAppointment_NotFound() {
        // Arrange
        int appointmentId = 999;
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> appointmentService.cancelAppointment(appointmentId));

        assertEquals("Appointment with ID " + appointmentId + " does not exist.", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(appointmentId);
        verify(appointmentRepository, never()).save(any(Appointment.class));
    }
}
