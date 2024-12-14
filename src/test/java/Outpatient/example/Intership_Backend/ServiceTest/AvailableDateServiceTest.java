//package Outpatient.example.Intership_Backend.ServiceTest;
//
//import Outpatient.example.Intership_Backend.Entity.AvailableDate;
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import Outpatient.example.Intership_Backend.Repository.AvailableDateRepository;
//import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
//import Outpatient.example.Intership_Backend.Service.AvailableDateService;
//import Outpatient.example.Intership_Backend.Service.DoctorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//
//class AvailableDateServiceTest {
//
//    @Mock
//    private AvailableDateRepository availableDateRepository;
//
//    @Mock
//    private DoctorRepository doctorRepository;
//
//    @Mock
//    private DoctorService doctorService;
//
//    @InjectMocks
//    private AvailableDateService availableDateService;
//
//    private AvailableDate availableDate;
//    private Doctor doctor;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Initialize test data
//        doctor = new Doctor();
//        doctor.setEmail("doctor@example.com");
//        doctor.setDoctorName("Dr. John");
//
//        availableDate = new AvailableDate();
//        availableDate.setDoctor(doctor); // Set doctor object directly, not just the email
//        availableDate.setAvailableFromdate(LocalDate.now());
//        availableDate.setAvailableEnddate(LocalDate.now().plusDays(7));
//        availableDate.setAmSlotTiming("9:00 AM - 12:00 PM");
//        availableDate.setPmSlotTiming("1:00 PM - 5:00 PM");
//    }
//
//    @Test
//    void testGetAvailabilityByDoctor_Success() {
//        when(doctorService.getLoginEmail()).thenReturn("doctor@example.com");
//        when(availableDateRepository.findByDoctorEmail("doctor@example.com")).thenReturn(availableDate);
//
//        AvailableDate fetchedAvailability = availableDateService.getAvailabilityByDoctor();
//
//        assertNotNull(fetchedAvailability);
//        assertEquals("doctor@example.com", fetchedAvailability.getDoctor().getEmail()); // Access doctor email through doctor object
//    }
//
//    @Test
//    void testGetAvailabilityByDoctor_NotFound() {
//        when(doctorService.getLoginEmail()).thenReturn("doctor@example.com");
//        when(availableDateRepository.findByDoctorEmail("doctor@example.com")).thenReturn(null);
//
//        AvailableDate fetchedAvailability = availableDateService.getAvailabilityByDoctor();
//
//        assertNull(fetchedAvailability);
//    }
//
//    @Test
//    void testUpdateAvailability_NewAvailability() {
//        when(doctorService.getLoginEmail()).thenReturn("doctor@example.com");
//        when(availableDateRepository.findByDoctorEmail("doctor@example.com")).thenReturn(null);
//        when(doctorRepository.findByEmail("doctor@example.com")).thenReturn(doctor);
//        when(availableDateRepository.save(any(AvailableDate.class))).thenReturn(availableDate);
//
//        AvailableDate updatedAvailability = availableDateService.updateAvailability(availableDate);
//
//        assertNotNull(updatedAvailability);
//        assertEquals("doctor@example.com", updatedAvailability.getDoctor().getEmail()); // Check doctor email
//        assertEquals("9:00 AM - 12:00 PM", updatedAvailability.getAmSlotTiming()); // Check if updated
//    }
//
//    @Test
//    void testUpdateAvailability_ExistingAvailability() {
//        AvailableDate existingAvailability = new AvailableDate();
//        existingAvailability.setDoctor(doctor); // Ensure doctor is set properly
//        existingAvailability.setAvailableFromdate(LocalDate.now().plusDays(1));
//        existingAvailability.setAvailableEnddate(LocalDate.now().plusDays(7));
//        existingAvailability.setAmSlotTiming("8:00 AM - 11:00 AM");
//        existingAvailability.setPmSlotTiming("12:00 PM - 4:00 PM");
//
//        when(doctorService.getLoginEmail()).thenReturn("doctor@example.com");
//        when(availableDateRepository.findByDoctorEmail("doctor@example.com")).thenReturn(existingAvailability);
//        when(availableDateRepository.save(any(AvailableDate.class))).thenReturn(existingAvailability);
//
//        AvailableDate updatedAvailability = availableDateService.updateAvailability(availableDate);
//
//        assertNotNull(updatedAvailability);
//        assertEquals("doctor@example.com", updatedAvailability.getDoctor().getEmail()); // Check doctor email
//        assertEquals("9:00 AM - 12:00 PM", updatedAvailability.getAmSlotTiming()); // Check if updated
//    }
//}
//package Outpatient.example.Intership_Backend.ServiceTest;
//
//import Outpatient.example.Intership_Backend.Entity.AvailableDate;
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import Outpatient.example.Intership_Backend.Repository.AvailableDateRepository;
//import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
//import Outpatient.example.Intership_Backend.Service.AvailableDateService;
//import Outpatient.example.Intership_Backend.Service.DoctorService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//class AvailableDateServiceTest {
//
//    @InjectMocks
//    private AvailableDateService availableDateService;
//
//    @Mock
//    private AvailableDateRepository availableDateRepository;
//
//    @Mock
//    private DoctorService doctorService;
//
//    @Mock
//    private DoctorRepository doctorRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testUpdateAvailability_ExistingSlot() {
//        // Arrange
//        String doctorEmail = "doctor@example.com";
//        AvailableDate existingSlot = new AvailableDate();
//        existingSlot.setAvailableFromDate("2023-12-01");
//        existingSlot.setAvailableEndDate("2023-12-10");
//
//        AvailableDate newSlot = new AvailableDate();
//        newSlot.setAvailableFromDate("2023-12-05");
//        newSlot.setAvailableEndDate("2023-12-15");
//
//        when(doctorService.getLoginEmail()).thenReturn(doctorEmail);
//        when(availableDateRepository.findByDoctorEmail(doctorEmail)).thenReturn(existingSlot);
//        when(availableDateRepository.save(existingSlot)).thenReturn(existingSlot);
//
//        // Act
//        AvailableDate updatedSlot = availableDateService.updateAvailability(newSlot);
//
//        // Assert
//        assertNotNull(updatedSlot);
//        assertEquals("2023-12-05", updatedSlot.getAvailableFromDate());
//        assertEquals("2023-12-15", updatedSlot.getAvailableEndDate());
//        verify(availableDateRepository).save(existingSlot);
//    }
//
//    @Test
//    void testUpdateAvailability_NewSlot() {
//        // Arrange
//        String doctorEmail = "doctor@example.com";
//        Doctor doctor = new Doctor();
//        doctor.setEmail(doctorEmail);
//
//        AvailableDate newSlot = new AvailableDate();
//        newSlot.setAvailableFromDate("2023-12-05");
//        newSlot.setAvailableEndDate("2023-12-15");
//
//        when(doctorService.getLoginEmail()).thenReturn(doctorEmail);
//        when(availableDateRepository.findByDoctorEmail(doctorEmail)).thenReturn(null);
//        when(doctorRepository.findByEmail(doctorEmail)).thenReturn(doctor);
//        when(availableDateRepository.save(newSlot)).thenReturn(newSlot);
//
//        // Act
//        AvailableDate savedSlot = availableDateService.updateAvailability(newSlot);
//
//        // Assert
//        assertNotNull(savedSlot);
//        assertEquals("2023-12-05", savedSlot.getAvailableFromDate());
//        assertEquals("2023-12-15", savedSlot.getAvailableEndDate());
//        verify(availableDateRepository).save(newSlot);
//    }
//
//    @Test
//    void testGetAvailabilityByDoctorEmail() {
//        // Arrange
//        String doctorEmail = "doctor@example.com";
//        AvailableDate availableDate = new AvailableDate();
//        availableDate.setAvailableFromDate("2023-12-05");
//        availableDate.setAvailableEndDate("2023-12-15");
//
//        when(availableDateRepository.findByDoctorEmail(doctorEmail)).thenReturn(availableDate);
//
//        // Act
//        AvailableDate result = availableDateService.getAvailabilityByDoctorEmail(doctorEmail);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals("2023-12-05", result.getAvailableFromDate());
//        assertEquals("2023-12-15", result.getAvailableEndDate());
//        verify(availableDateRepository).findByDoctorEmail(doctorEmail);
//    }
//}
package Outpatient.example.Intership_Backend.ServiceTest;

import Outpatient.example.Intership_Backend.Entity.AvailableDate;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Repository.AvailableDateRepository;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import Outpatient.example.Intership_Backend.Service.AvailableDateService;
import Outpatient.example.Intership_Backend.Service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AvailableDateServiceTest {

    @InjectMocks
    private AvailableDateService availableDateService;

    @Mock
    private AvailableDateRepository availableDateRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private DoctorRepository doctorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateAvailability_ExistingSlot() {
        // Arrange
        String doctorEmail = "doctor@example.com";
        AvailableDate existingSlot = new AvailableDate();
        existingSlot.setAvailableFromDate(LocalDate.of(2023, 12, 1));
        existingSlot.setAvailableEndDate(LocalDate.of(2023, 12, 10));

        AvailableDate newSlot = new AvailableDate();
        newSlot.setAvailableFromDate(LocalDate.of(2023, 12, 5));
        newSlot.setAvailableEndDate(LocalDate.of(2023, 12, 15));

        when(doctorService.getLoginEmail()).thenReturn(doctorEmail);
        when(availableDateRepository.findByDoctorEmail(doctorEmail)).thenReturn(existingSlot);
        when(availableDateRepository.save(existingSlot)).thenReturn(existingSlot);

        // Act
        AvailableDate updatedSlot = availableDateService.updateAvailability(newSlot);

        // Assert
        assertNotNull(updatedSlot);
        assertEquals(LocalDate.of(2023, 12, 5), updatedSlot.getAvailableFromDate());
        assertEquals(LocalDate.of(2023, 12, 15), updatedSlot.getAvailableEndDate());
        verify(availableDateRepository).save(existingSlot);
    }

    @Test
    void testUpdateAvailability_NewSlot() {
        // Arrange
        String doctorEmail = "doctor@example.com";
        Doctor doctor = new Doctor();
        doctor.setEmail(doctorEmail);

        AvailableDate newSlot = new AvailableDate();
        newSlot.setAvailableFromDate(LocalDate.of(2023, 12, 5));
        newSlot.setAvailableEndDate(LocalDate.of(2023, 12, 15));

        when(doctorService.getLoginEmail()).thenReturn(doctorEmail);
        when(availableDateRepository.findByDoctorEmail(doctorEmail)).thenReturn(null);
        when(doctorRepository.findByEmail(doctorEmail)).thenReturn(doctor);
        when(availableDateRepository.save(newSlot)).thenReturn(newSlot);

        // Act
        AvailableDate savedSlot = availableDateService.updateAvailability(newSlot);

        // Assert
        assertNotNull(savedSlot);
        assertEquals(LocalDate.of(2023, 12, 5), savedSlot.getAvailableFromDate());
        assertEquals(LocalDate.of(2023, 12, 15), savedSlot.getAvailableEndDate());
        verify(availableDateRepository).save(newSlot);
    }

    @Test
    void testGetAvailabilityByDoctorEmail() {
        // Arrange
        String doctorEmail = "doctor@example.com";
        AvailableDate availableDate = new AvailableDate();
        availableDate.setAvailableFromDate(LocalDate.of(2023, 12, 5));
        availableDate.setAvailableEndDate(LocalDate.of(2023, 12, 15));

        when(availableDateRepository.findByDoctorEmail(doctorEmail)).thenReturn(availableDate);

        // Act
        AvailableDate result = availableDateService.getAvailabilityByDoctorEmail(doctorEmail);

        // Assert
        assertNotNull(result);
        assertEquals(LocalDate.of(2023, 12, 5), result.getAvailableFromDate());
        assertEquals(LocalDate.of(2023, 12, 15), result.getAvailableEndDate());
        verify(availableDateRepository).findByDoctorEmail(doctorEmail);
    }
}


