package Outpatient.example.Intership_Backend.Service;

import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Entity.Patient;
import Outpatient.example.Intership_Backend.Repository.AppointmentRepository;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import Outpatient.example.Intership_Backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    public Appointment bookAppointment(Appointment appointment) {
        Patient patient = patientRepository.findByEmail(patientService.getLoginEmail());


        Doctor doctor = doctorRepository.findByEmail(appointment.getDoctorEmail());
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor with email " + appointment.getDoctorEmail() + " does not exist.");
        }


        appointment.setPatient(patient);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(int id) {
        return appointmentRepository.findById(id);
    }

    public void cancelAppointment(int id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        if (appointment.isPresent()) {

            Appointment existingAppointment = appointment.get();
            existingAppointment.setStatus("Cancelled");
            appointmentRepository.save(existingAppointment);
        } else {
            throw new IllegalArgumentException("Appointment with ID " + id + " does not exist.");
        }
    }


}
