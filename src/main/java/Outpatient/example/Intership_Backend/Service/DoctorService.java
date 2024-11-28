package Outpatient.example.Intership_Backend.Service;

import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.DTO.LoginRequest;
import Outpatient.example.Intership_Backend.DTO.RegisterUserDTo;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class DoctorService {


    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    String registerEmail;
    String loginEmail;



    public void createDoctor(RegisterUserDTo registerUserDTo) {

        Doctor doctor = new Doctor();
        registerEmail= registerUserDTo.getEmail();
        doctor.setEmail(registerUserDTo.getEmail());
        doctorRepository.save(doctor);
    }






    public ResponseEntity<ApiError> updateDoctorProfile(Doctor doctorDTO) {

        System.out.println(loginEmail);
        Doctor existingDoctor = doctorRepository.findByEmail(loginEmail)
                .orElseThrow(()-> new RuntimeException("DOCTOR NOT FOUND"));

        if (existingDoctor == null) {
            ApiError errorResponse = ApiError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Doctor not found or unauthorized access")
                    .subErrors(null)
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        existingDoctor.setDoctorName(doctorDTO.getDoctorName());
        existingDoctor.setSpeciality(doctorDTO.getSpeciality());
        existingDoctor.setLocation(doctorDTO.getLocation());
        existingDoctor.setMobileNo(doctorDTO.getMobileNo());
        existingDoctor.setHospitalName(doctorDTO.getHospitalName());
        existingDoctor.setChargedPerVisit(doctorDTO.getChargedPerVisit());

        // Save the updated doctor
        doctorRepository.save(existingDoctor);

        // Return success response
        ApiError successResponse = ApiError.builder()
                .status(HttpStatus.OK)
                .message("Doctor profile updated successfully")
                .subErrors(null)
                .build();
        return ResponseEntity.ok(successResponse);
    }




    public void LoginDoctor(LoginRequest loginRequest) {
        loginEmail= loginRequest.getEmail();

    }



    public Doctor getDoctorProfile() {
        return doctorRepository.findByEmail(loginEmail)
                .orElse(null); // Return null if doctor is not found
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }


//    public ResponseEntity<ApiError> getAllDoctors() {
//        List<Doctor> doctors = doctorRepository.findAll();
//        if (!doctors.isEmpty()) {
//            return ResponseEntity.ok(
//                    ApiError.builder()
//                            .status(HttpStatus.OK)
//                            .message("Doctors fetched successfully")
//                            .subErrors(doctors.stream().map(Doctor::toString).toList())
//                            .build()
//            );
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    ApiError.builder()
//                            .status(HttpStatus.NOT_FOUND)
//                            .message("No doctors found")
//                            .build()
//            );
//        }
//}










}

