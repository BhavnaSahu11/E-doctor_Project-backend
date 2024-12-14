//package Outpatient.example.Intership_Backend.Controller;
//
//import Outpatient.example.Intership_Backend.Entity.Appointment;
//import Outpatient.example.Intership_Backend.Service.AppointmentService;
//import Outpatient.example.Intership_Backend.Service.PaymentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/appointments")
//public class AppointmentController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @PostMapping("/book")
//    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
////        try {
////            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
////
////
////            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
////        } catch (IllegalArgumentException e) {
////            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
////        }
////    }
//
//        try {
////            // Retrieve the logged-in patient's email from the security context
////            String patientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
////
////            // Set the patient's email to the appointment object
////            appointment.setPatientEmail(patientEmail);
//
//            // First, book the appointment
//            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
//
//            // If-Else condition based on the payment mode
//            if ("ONLINE_PAY".equalsIgnoreCase(appointment.getPaymentmode())) {
//                // Handle the online payment logic here
//                double chargePerVisit = (appointment.getDoctorEmail());
//                paymentService.createCheckoutSession(savedAppointment.getId(), chargePerVisit);
//                savedAppointment.setStatus("PAYMENT_PENDING"); // Set status to indicate payment is pending
//            } else if ("CASH".equalsIgnoreCase(appointment.getPaymentmode())) {
//                // If payment mode is CASH, no need to create a payment record
//                savedAppointment.setStatus("BOOKED"); // Mark appointment as booked
//            } else {
//                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Invalid payment mode
//            }
//
//            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Appointment>> getAllAppointments() {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//
//
//
//
//}
//package Outpatient.example.Intership_Backend.Controller;
//
//import Outpatient.example.Intership_Backend.Entity.Appointment;
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import Outpatient.example.Intership_Backend.Service.AppointmentService;
//import Outpatient.example.Intership_Backend.Service.PaymentService;
//import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/appointments")
//public class AppointmentController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    // Book Appointment endpoint
//    @PostMapping("/book")
//    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
//        try {
//            // Save the appointment in the database
//            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
//
//            // Check if payment mode is online
//            if ("ONLINE_PAY".equals(appointment.getPaymentmode())) {
//                // Fetch the doctor by email from the Doctor repository using the doctor's email from the appointment
//                Doctor doctor = doctorRepository.findByEmail(appointment.getDoctorEmail());
//
//                if (doctor == null) {
//                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Doctor not found
//                }
//
//                // Fetch the charge per visit from the doctor's entity
//                double chargePerVisit = doctor.getChargePerVisit(); // Assuming chargePerVisit is a field in the Doctor entity
//
//                // Call the PaymentService to create a Stripe checkout session
//                String checkoutUrl = paymentService.createCheckoutSession(savedAppointment.getId().toString(), chargePerVisit);
//
//                // Return the checkout URL to the client for redirection to payment gateway
//                return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(savedAppointment, checkoutUrl));
//            }
//
//            // If payment mode is cash, simply return the appointment as booked
//            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
//
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // Get all appointments
//    @GetMapping
//    public ResponseEntity<List<Appointment>> getAllAppointments() {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//
//    // Additional response wrapper to include the checkout URL
//    public static class AppointmentResponse {
//        private Appointment appointment;
//        private String checkoutUrl;
//
//        public AppointmentResponse(Appointment appointment, String checkoutUrl) {
//            this.appointment = appointment;
//            this.checkoutUrl = checkoutUrl;
//        }
//
//        public Appointment getAppointment() {
//            return appointment;
//        }
//
//        public String getCheckoutUrl() {
//            return checkoutUrl;
//        }
//    }
//}
//package Outpatient.example.Intership_Backend.Controller;
//
//import Outpatient.example.Intership_Backend.Entity.Appointment;
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import Outpatient.example.Intership_Backend.Service.AppointmentService;
//import Outpatient.example.Intership_Backend.Service.PaymentService;
//import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/appointments")
//public class AppointmentController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    // Book Appointment endpoint
//    @PostMapping("/book")
//    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
//        try {
//            // Save the appointment in the database
//            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
//
//            // Check if payment mode is online
//            if ("ONLINE_PAY".equals(appointment.getPaymentmode())) {
//                // Fetch the doctor by email from the Doctor repository using the doctor's email from the appointment
//                Doctor doctor = doctorRepository.findByEmail(appointment.getDoctorEmail());
//
//                if (doctor == null) {
//                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Doctor not found
//                }
//
//                // Fetch the charge per visit from the doctor's entity
//                double chargePerVisit = doctor.getChargedPerVisit(); // Get charge per visit
//
//                // Call the PaymentService to create a Stripe checkout session
//                String checkoutUrl = paymentService.createCheckoutSession(savedAppointment.getId().toString(), chargePerVisit);
//
//                // Return the checkout URL to the client for redirection to payment gateway
//                return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(savedAppointment, checkoutUrl));
//            }
//
//            // If payment mode is cash, simply return the appointment as booked
//            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
//
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // Get all appointments
//    @GetMapping
//    public ResponseEntity<List<Appointment>> getAllAppointments() {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//
//    // Additional response wrapper to include the checkout URL
////    public static class AppointmentResponse {
////        private Appointment appointment;
////        private String checkoutUrl;
////
////        public AppointmentResponse(Appointment appointment, String checkoutUrl) {
////            this.appointment = appointment;
////            this.checkoutUrl = checkoutUrl;
////        }
////
////        public Appointment getAppointment() {
////            return appointment;
////        }
////
////        public String getCheckoutUrl() {
////            return checkoutUrl;
////        }
//   // }
//}
//package Outpatient.example.Intership_Backend.Controller;
//
//import Outpatient.example.Intership_Backend.Entity.Appointment;
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import Outpatient.example.Intership_Backend.Service.AppointmentService;
//import Outpatient.example.Intership_Backend.Service.PaymentService;
//import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import Outpatient.example.Intership_Backend.DTO.AppointmentResponse;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/appointments")
//public class AppointmentController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    @PostMapping("/book")
//    public ResponseEntity<?> bookAppointment(@RequestBody Appointment appointment) {
//        try {
//            // Save the appointment
//            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
//
//            // If payment mode is online, initiate Stripe checkout
//            if ("ONLINE_PAY".equals(appointment.getPaymentmode())) {
//                // Fetch doctor details by email
//                Doctor doctor = doctorRepository.findByEmail(appointment.getDoctorEmail());
//
//                if (doctor == null) {
//                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // Doctor not found
//                }
//
//                // Fetch the charge per visit from the doctor entity
//                double chargePerVisit = doctor.getChargedPerVisit();
//
//                // Create Stripe checkout session and return URL
//                String checkoutUrl = paymentService.createCheckoutSession(savedAppointment.getId().toString(), chargePerVisit);
//
//                // Return appointment with checkout URL for redirection
//                return ResponseEntity.status(HttpStatus.CREATED).body(new AppointmentResponse(savedAppointment, checkoutUrl));
//            }
//
//            // If payment is via cash, simply return the appointment
//            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
//
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//
////    @GetMapping("/payment-success")
////    public ResponseEntity<?> paymentSuccess(@RequestParam String appointmentId, @RequestParam String sessionId) {
////        // Verify payment status via Stripe session
////        boolean paymentVerified = paymentService.verifyPayment(sessionId);
////
////        if (paymentVerified) {
////            // Update appointment status to "confirmed" or any other logic
////
////            Appointment appointment = appointmentService.getAppointmentById(Long.parseLong(appointmentId));
////            if (appointment != null) {
////                appointment.setStatus("CONFIRMED");
////                appointmentService.updateAppointment(appointment);
////                return new ResponseEntity<>("Payment successful, appointment confirmed!", HttpStatus.OK);
////            } else {
////                return new ResponseEntity<>("Appointment not found", HttpStatus.BAD_REQUEST);
////            }
////        } else {
////            return new ResponseEntity<>("Payment failed or not completed", HttpStatus.BAD_REQUEST);
////        }
////    }
//@GetMapping("/payment-success")
//public ResponseEntity<?> paymentSuccess(@RequestParam String appointmentId, @RequestParam String sessionId) {
//    try {
//        // Verify payment status via Stripe session
//        boolean paymentVerified = paymentService.verifyPayment(sessionId);
//
//        if (paymentVerified) {
//            // Safely parse the appointmentId to Long
//            Long appointmentIdLong = Long.parseLong(appointmentId); // Convert the string to Long
//
//            // Get the appointment using the appointmentId
//            //Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(appointmentIdLong);
//            Optional<Appointment> appointmentOptional = appointmentService.getAppointmentById(appointmentIdLong.intValue());
//            if (appointmentOptional.isPresent()) {
//                Appointment appointment = appointmentOptional.get();  // Unwrap the Optional
//
//                // Update status to confirmed
//                appointment.setStatus("CONFIRMED");
//                appointmentService.updateAppointment(appointment);
//
//                // Process payment confirmation for CASH or ONLINE
//                if (appointment.getPaymentmode().equals("CASH")) {
//                    return new ResponseEntity<>("Appointment confirmed via cash payment", HttpStatus.OK);
//                }
//
//                return new ResponseEntity<>("Payment successful, appointment confirmed!", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Appointment not found", HttpStatus.BAD_REQUEST);
//            }
//        } else {
//            return new ResponseEntity<>("Payment failed or not completed", HttpStatus.BAD_REQUEST);
//        }
//    } catch (NumberFormatException e) {
//        return new ResponseEntity<>("Invalid appointment ID format", HttpStatus.BAD_REQUEST);
//    }
//}
//
//
//    @GetMapping("/payment-cancelled")
//    public ResponseEntity<String> paymentCancelled() {
//        return new ResponseEntity<>("Payment cancelled by user", HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Appointment>> getAllAppointments() {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//}

package Outpatient.example.Intership_Backend.Controller;
//
//import Outpatient.example.Intership_Backend.Entity.Appointment;
//import Outpatient.example.Intership_Backend.Service.AppointmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import Outpatient.example.Intership_Backend.Service.PaymentService;
//import java.util.List;
//import Outpatient.example.Intership_Backend.Entity.Doctor;
//import java.util.Optional;
//
//
//@RestController
//@RequestMapping("/appointments")
//public class AppointmentController {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//
//    @Autowired
//    private PaymentService paymentService;
////    @PostMapping("/book")
////    public ResponseEntity<Appointment> bookAppointment(@RequestBody Appointment appointment) {
////        try {
////            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
////            return new ResponseEntity<>(savedAppointment, HttpStatus.CREATED);
////        } catch (IllegalArgumentException e) {
////            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
////        }
////    }
//
//    //code me
//    @PostMapping("/book")
//    public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
//        try {
//            Appointment savedAppointment = appointmentService.bookAppointment(appointment);
//           // String paymentUrl = paymentService.createCheckoutSession(savedAppointment.getId().toString(), savedAppointment.getChargedPerVisit());
//             String paymentUrl = paymentService.createCheckoutSession(savedAppointment.getId().toString(), savedAppointment.getDoctorFees());
//            return new ResponseEntity<>(paymentUrl, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//    @GetMapping("/payment-success")
//    public ResponseEntity<String> paymentSuccess(@RequestParam String appointmentId) {
//        // Mark the appointment as "Paid" or perform necessary post-payment updates
//        return ResponseEntity.ok("Payment successful for appointment ID: " + appointmentId);
//    }
//
//    @GetMapping("/payment-cancelled")
//    public ResponseEntity<String> paymentCancelled() {
//        return ResponseEntity.ok("Payment was cancelled.");
//    }
//
//    //code me
//    @GetMapping
//    public ResponseEntity<List<Appointment>> getAllAppointments() {
//        List<Appointment> appointments = appointmentService.getAllAppointments();
//        return ResponseEntity.ok(appointments);
//    }
//
//
//
//
//
//
//}

import Outpatient.example.Intership_Backend.Entity.Appointment;
import Outpatient.example.Intership_Backend.Repository.AppointmentRepository;
import Outpatient.example.Intership_Backend.Service.AppointmentService;
import Outpatient.example.Intership_Backend.Service.PaymentService;
import Outpatient.example.Intership_Backend.Entity.Doctor;
import Outpatient.example.Intership_Backend.Repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DoctorRepository doctorRepository;  // Autowire the DoctorRepository
//change
    @Autowired
    private AppointmentRepository appointmentRepository;

@PostMapping("/book")
public ResponseEntity<String> bookAppointment(@RequestBody Appointment appointment) {
    try {
        // Fetch doctor details based on the doctor's email from appointment
        Optional<Doctor> doctorOptional = doctorRepository.findById(appointment.getDoctorEmail());

        if (!doctorOptional.isPresent()) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = doctorOptional.get();
        double chargedPerVisit = doctor.getChargedPerVisit(); // Get charged per visit from the Doctor entity

        // Book the appointment
        Appointment savedAppointment = appointmentService.bookAppointment(appointment);

        // Handle payment mode logic
        if ("CASH".equalsIgnoreCase(appointment.getPaymentmode())) {
            // If payment is Cash, mark the appointment as paid directly (or any other logic as needed)

            return new ResponseEntity<>("Appointment booked Successfully", HttpStatus.CREATED);
        } else if ("ONLINE_PAY".equalsIgnoreCase(appointment.getPaymentmode())) {
            // If payment is Online, generate the payment URL
            String paymentUrl = paymentService.createCheckoutSession(savedAppointment.getId().toString(), chargedPerVisit);
            return new ResponseEntity<>(paymentUrl, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Invalid payment mode", HttpStatus.BAD_REQUEST);
        }
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}

//right
    @GetMapping("/payment-success")
    public ResponseEntity<String> paymentSuccess(@RequestParam String appointmentId) {
        // Mark the appointment as "Paid" or perform necessary post-payment updates
        URI redirectUri = URI.create("http://localhost:3000/payment-success?appointmentId=" + appointmentId);
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();

       // return ResponseEntity.ok("Payment successful for appointment ID: " + appointmentId);
    }
//right

    @GetMapping("/payment-cancelled")
    public ResponseEntity<String> paymentCancelled() {
        return ResponseEntity.ok("Payment was cancelled.");
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
}

