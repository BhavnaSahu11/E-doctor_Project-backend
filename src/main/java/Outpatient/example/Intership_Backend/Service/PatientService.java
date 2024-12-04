package Outpatient.example.Intership_Backend.Service;

import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.DTO.LoginRequest;
import Outpatient.example.Intership_Backend.DTO.RegisterUserDTo;
import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Entity.Patient;
import Outpatient.example.Intership_Backend.Repository.AppointmentRepository;
import Outpatient.example.Intership_Backend.Repository.PatientRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    String registerEmail;
    String loginEmail;



    public void createPatient(RegisterUserDTo registerUserDTo) {
        Patient patient = new Patient();
        registerEmail= registerUserDTo.getEmail();
        patient.setEmail(registerUserDTo.getEmail());
        patientRepository.save(patient);
    }



    public void loginPatient(LoginRequest loginRequest) {
        loginEmail= loginRequest.getEmail();
    }

    public Patient getPatientProfile() {
        return patientRepository.findByEmail(loginEmail);// Return null if doctor is not found
    }

    public List<Patient> getAllDoctors() {
        return patientRepository.findAll();
    }


    public ResponseEntity<ApiError> updateDoctorProfile(Patient patientDto) {

        System.out.println(loginEmail);
        Patient existingPatient = patientRepository.findByEmail(loginEmail);

        if (existingPatient == null) {
            ApiError errorResponse = ApiError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Patient not found ")
                    .subErrors(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        existingPatient.setPatientName(patientDto.getPatientName());
        existingPatient.setAge(patientDto.getAge());
        existingPatient.setGender(patientDto.getGender());
        existingPatient.setMobileNo(patientDto.getMobileNo());
        existingPatient.setBloodGroup(patientDto.getBloodGroup());
        existingPatient.setAddress(patientDto.getAddress());

        patientRepository.save(existingPatient);


        ApiError successResponse = ApiError.builder()
                .status(HttpStatus.OK)
                .message("Patient profile updated successfully")
                .subErrors(null)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    public List<Appointment> getAppointmentsByPatientEmail() {
        return appointmentRepository.findByPatientEmail(loginEmail);
    }
}
