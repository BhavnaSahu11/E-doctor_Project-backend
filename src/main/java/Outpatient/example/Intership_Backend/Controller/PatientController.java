package Outpatient.example.Intership_Backend.Controller;


import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Entity.Patient;
import Outpatient.example.Intership_Backend.Service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/get-welcome-email")
    public ResponseEntity<Map<String, String>> getWelcomeEmail() {
        Map<String, String> response = new HashMap<>();
        response.put("email", patientService.getLoginEmail());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/profile")
    public ResponseEntity<Patient> getDoctorProfile() {
        Patient patient = patientService.getPatientProfile();
        if (patient != null) {
            return ResponseEntity.ok(patient);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }


    @PutMapping("/edit-profile")
    public ResponseEntity<ApiError> editDoctorProfile(@RequestBody @Valid Patient patient) {
        return patientService.updateDoctorProfile(patient);
    }

//    @GetMapping("/appointments")
//    public List<Appointment> getAppointmentsByPatientEmail() {
//        return patientService.getAppointmentsByPatientEmail();
//    }



    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAppointmentsByPatientEmail() {
        try {
            List<Appointment> appointments = patientService.getAppointmentsByPatientEmail();
            return ResponseEntity.ok(appointments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable int id) {
        boolean isCancelled = patientService.cancelAppointment(id);
        if (isCancelled) {
            return ResponseEntity.ok("Appointment cancelled successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found.");
    }

}