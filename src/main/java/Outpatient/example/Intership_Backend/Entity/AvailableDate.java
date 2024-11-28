package Outpatient.example.Intership_Backend.Entity;



import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate appointmentFromdate;

    private LocalDate appointmentEnddate;


    private String amSlotTiming;

    private String pmSlotTiming;


    @ManyToOne
    @JoinColumn(name = "doctor_email", nullable = false)
    private Doctor doctor;
}
