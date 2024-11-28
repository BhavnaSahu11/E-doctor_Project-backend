package Outpatient.example.Intership_Backend.Repository;


import Outpatient.example.Intership_Backend.Entity.AvailableDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AvailableDateRepository extends JpaRepository<AvailableDate, Long> {
    AvailableDate findByDoctorEmail(String doctorEmail);
}
