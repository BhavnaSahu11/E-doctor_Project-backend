//package Outpatient.example.Intership_Backend.Repository;
//
//import Outpatient.example.Intership_Backend.Entity.Appointment;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
//
//    List<Appointment> findByDoctorEmail(String doctorEmail);
//
//    List<Appointment> findByPatientEmail(String patientEmail);
//
//
//}
package Outpatient.example.Intership_Backend.Repository;

import Outpatient.example.Intership_Backend.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

    List<Appointment> findByDoctorEmail(String doctorEmail);

    List<Appointment> findByPatientEmail(String patientEmail);


    List<Appointment> findByDoctorEmailAndStatus(String doctorEmail, String status);

    @Query("SELECT a FROM Appointment a WHERE a.doctorEmail = :doctorEmail AND a.status IS NULL")
    List<Appointment> findByDoctorEmailAndStatusIsNull(String doctorEmail);


    // New method to fetch appointment by ID
    Appointment findById(Long id);


}