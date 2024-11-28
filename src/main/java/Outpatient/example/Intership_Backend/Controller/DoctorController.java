package Outpatient.example.Intership_Backend.Controller;


import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor/")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;


    @GetMapping("/get-welcome-email")
    public ResponseEntity<Map<String, String>> getWelcomeEmail() {
        Map<String, String> response = new HashMap<>();
        response.put("email", doctorService.getLoginEmail());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/profile")
    public ResponseEntity<Doctor> getDoctorProfile() {
        Doctor doctor = doctorService.getDoctorProfile();
        if (doctor != null) {
            return ResponseEntity.ok(doctor);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }


    @PutMapping("/edit-profile")
    public ResponseEntity<ApiError> editDoctorProfile(@RequestBody @Valid Doctor doctor) {
        return doctorService.updateDoctorProfile(doctor);
    }


    @GetMapping("/get-all-doctors")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors(); // Ensure this service is returning valid data
        return ResponseEntity.ok(doctors);
    }

}
