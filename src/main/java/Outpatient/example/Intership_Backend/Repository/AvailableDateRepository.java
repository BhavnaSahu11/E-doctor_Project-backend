package Outpatient.example.Intership_Backend.Repository;


import Outpatient.example.Intership_Backend.Entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {
    AvailableDate findByDoctorEmail(String doctorEmail);
}
