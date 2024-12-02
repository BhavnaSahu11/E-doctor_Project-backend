package Outpatient.example.Intership_Backend.Service;



import Outpatient.example.Intership_Backend.Entity.AvailableDate;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Repository.AvailableDateRepository;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import Outpatient.example.Intership_Backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailableDateService {

    @Autowired
    private AvailableDateRepository availableDateRepository;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;



    public AvailableDate getAvailabilityByDoctor() {
        return availableDateRepository.findByDoctorEmail(doctorService.getLoginEmail());
    }



    public AvailableDate updateAvailability( AvailableDate availableDate) {

        AvailableDate existingSlot = availableDateRepository.findByDoctorEmail(doctorService.getLoginEmail());
        if (existingSlot != null) {
            existingSlot.setAvailableFromdate(availableDate.getAvailableFromdate());
            existingSlot.setAvailableEnddate(availableDate.getAvailableEnddate());
            existingSlot.setAmSlotTiming(availableDate.getAmSlotTiming());
            existingSlot.setPmSlotTiming(availableDate.getPmSlotTiming());

            return availableDateRepository.save(existingSlot);
        } else {
            Doctor doctor = doctorRepository.findByEmail(doctorService.getLoginEmail());

            availableDate.setDoctor(doctor);
            return availableDateRepository.save(availableDate);
        }


    }

}