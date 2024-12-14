package Outpatient.example.Intership_Backend.ServiceTest;

import Outpatient.example.Intership_Backend.Advices.ApiError;
import Outpatient.example.Intership_Backend.DTO.LoginRequest;
import Outpatient.example.Intership_Backend.DTO.RegisterUserDTo;
import Outpatient.example.Intership_Backend.Entity.User;
import Outpatient.example.Intership_Backend.Repository.UserRepo;
import Outpatient.example.Intership_Backend.Service.DoctorService;
import Outpatient.example.Intership_Backend.Service.PatientService;
import Outpatient.example.Intership_Backend.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUsers() {
        userService.loadUsers();
        verify(userRepo, times(1)).saveAll(anyList());
    }

    @Test
    void testRegisterNewUser_EmailAlreadyExists() {
        RegisterUserDTo userDto = new RegisterUserDTo("test", "test@gmail.com", "password", "password", "USER");
        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));

        ResponseEntity<ApiError> response = userService.registerNewUser(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already exists", response.getBody().getMessage());
    }

    @Test
    void testRegisterNewUser_PasswordsDoNotMatch() {
        RegisterUserDTo userDto = new RegisterUserDTo("test", "test@gmail.com", "password", "differentPassword", "USER");
        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<ApiError> response = userService.registerNewUser(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Passwords do not match", response.getBody().getMessage());
    }

    @Test
    void testRegisterNewUser_SuccessDoctorRole() {
        RegisterUserDTo userDto = new RegisterUserDTo("test", "test@gmail.com", "password", "password", "DOCTOR");
        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<ApiError> response = userService.registerNewUser(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registered Successfully", response.getBody().getMessage());
        verify(doctorService, times(1)).createDoctor(userDto);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterNewUser_SuccessUserRole() {
        RegisterUserDTo userDto = new RegisterUserDTo("test", "test@gmail.com", "password", "password", "USER");
        when(userRepo.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        ResponseEntity<ApiError> response = userService.registerNewUser(userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registered Successfully", response.getBody().getMessage());
        verify(patientService, times(1)).createPatient(userDto);
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testFindByEmail_Found() {
        String email = "test@gmail.com";
        User user = new User();
        user.setEmail(email);
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        String email = "notfound@gmail.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail(email);

        assertFalse(result.isPresent());
    }

    @Test
    void testGetUserRoleByEmail_Success() {
        when(userRepo.findRoleByEmail("test@gmail.com")).thenReturn(Optional.of("USER"));

        String role = userService.getUserRoleByEmail("test@gmail.com");

        assertEquals("USER", role);
    }

    @Test
    void testGetUserRoleByEmail_UserNotFound() {
        when(userRepo.findRoleByEmail("test@gmail.com")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.getUserRoleByEmail("test@gmail.com"));

        assertEquals("No user found with the provided email", exception.getMessage());
    }

    @Test
    void testAuthenticate_Success() {
        String email = "test@gmail.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);

        assertTrue(userService.authenticate(email, password));
    }

    @Test
    void testAuthenticate_Failure() {
        String email = "test@gmail.com";
        String password = "wrongPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        assertFalse(userService.authenticate(email, password));
    }

//    @Test
//    void testAuthenticateUser_UserNotFound() {
//        LoginRequest loginRequest = new LoginRequest("notfound@gmail.com", "password");
//        when(userRepo.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());
//
//        ApiError response = userService.authenticateUser(loginRequest);
//
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
//        assertEquals("User not found", response.getMessage());
//    }

    @Test
    void testAuthenticateUser_InvalidPassword() {
        LoginRequest loginRequest = new LoginRequest("test@gmail.com", "wrongPassword");
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        when(userRepo.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())).thenReturn(false);

        ApiError response = userService.authenticateUser(loginRequest);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatus());
        assertEquals("Invalid password", response.getMessage());
    }
}
