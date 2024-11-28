package Outpatient.example.Intership_Backend.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private String doctorName;

    private String speciality;

    private String location;

    private String mobileNo;

    @Id
    private String email;

    private String hospitalName;

    private double chargedPerVisit;

}