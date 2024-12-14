//package Outpatient.example.Intership_Backend.Repository;
//
//
//
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//
//@Repository
//public interface DoctorRepository extends JpaRepository<Doctor, String> {
//
//
//    Doctor findByEmail(String email);
//
//
//
//
//}
package Outpatient.example.Intership_Backend.Repository;



import Outpatient.example.Intership_Backend.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, String> {


    Doctor findByEmail(String email);


}

