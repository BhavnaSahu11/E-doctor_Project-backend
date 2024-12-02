package Outpatient.example.Intership_Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String doctorEmail;

    @ManyToOne
    @JoinColumn(name = "patient_email")
    private Patient patient;

    private LocalDate appointmentDate;

    private String reason;

    private String status;

    private String remarks;
}
