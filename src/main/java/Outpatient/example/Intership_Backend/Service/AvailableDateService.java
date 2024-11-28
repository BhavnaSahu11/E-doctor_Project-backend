package Outpatient.example.Intership_Backend.Service;



import Outpatient.example.Intership_Backend.Entity.AvailableDate;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Repository.AvailableDateRepository;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableDateService {

    @Autowired
    private AvailableDateRepository availableDateRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorService doctorService;



    public AvailableDate getAvailabilityByDoctor() {
        return availableDateRepository.findByDoctorEmail(doctorService.getLoginEmail());
    }



    public AvailableDate updateAvailability( AvailableDate availableDate) {

        AvailableDate existingSlot = availableDateRepository.findByDoctorEmail(doctorService.getLoginEmail());
        if (existingSlot != null) {
            existingSlot.setAppointmentFromdate(availableDate.getAppointmentFromdate());
            existingSlot.setAppointmentEnddate(availableDate.getAppointmentEnddate());
            existingSlot.setAmSlotTiming(availableDate.getAmSlotTiming());
            existingSlot.setPmSlotTiming(availableDate.getPmSlotTiming());

            return availableDateRepository.save(existingSlot);
        } else {
            Doctor doctor = doctorRepository.findByEmail(doctorService.getLoginEmail())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with email: " + doctorService.getLoginEmail()));

            availableDate.setDoctor(doctor);
            return availableDateRepository.save(availableDate);
        }


    }

}