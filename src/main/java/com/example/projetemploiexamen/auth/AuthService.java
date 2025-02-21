package com.example.projetemploiexamen.auth;


import com.example.projetemploiexamen.admin.Admin;
import com.example.projetemploiexamen.admin.AdminRepository;
import com.example.projetemploiexamen.admin.DTO.CreateAdminDTO;
import com.example.projetemploiexamen.auth.DTO.AuthRequestDTO;
import com.example.projetemploiexamen.auth.DTO.AuthResponseDTO;
import com.example.projetemploiexamen.auth.token.AdminAuthenticationToken;
import com.example.projetemploiexamen.auth.token.StudentAuthenticationToken;
import com.example.projetemploiexamen.student.DTO.CreateStudentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(AdminRepository adminRepository, JwtUtil jwtUtil , AuthenticationManager authenticationManager) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<ApiResponse<AuthResponseDTO>> registerAdmin(CreateAdminDTO createAdminDTO) {
        //todo all attempts to create an admin are accepted implement an verification by an other admin logic
        try {
            createAdminDTO.setPassword(passwordEncoder.encode(createAdminDTO.getPassword())); // Encrypt password
            Admin admin = new Admin(createAdminDTO); // Create an admin entity
            adminRepository.save(admin);  // Save admin to DB
            String token = jwtUtil.generateToken(admin.getEmail(), "ADMIN"); // Include name & role
            return ResponseEntity.ok(ApiResponse.success("Admin created successfully", new AuthResponseDTO(token)));

        } catch (DataIntegrityViolationException e) { // Handle email already exists case
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error("Email already registered"));
        } catch (Exception e) { // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Registration failed")    );
        }
    }


    public ResponseEntity<ApiResponse<AuthResponseDTO>> loginAdmin(AuthRequestDTO authRequest) {
        System.out.println("trying to verify info in the service ");
        Authentication authentication = authenticationManager.authenticate(

                new AdminAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            System.out.println("Admin logged in successfully");
           return ResponseEntity.ok(ApiResponse.success("login successfull ", new AuthResponseDTO(jwtUtil.generateToken(authRequest.getEmail(),"ADMIN"))));
        } else {
            System.out.println("Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid email or password"));

        }
    }

    public ResponseEntity<ApiResponse<AuthResponseDTO>> loginStudent(AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new StudentAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            System.out.println("Student logged in successfully");
            return ResponseEntity.ok(ApiResponse.success("login successfull ", new AuthResponseDTO(jwtUtil.generateToken(authRequest.getEmail(),"STUDENT"))));
        } else {
            System.out.println("Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Invalid email or password"));

        }
    }

    public ResponseEntity<ApiResponse<AuthResponseDTO>> registerStudent(CreateStudentDTO createStudentDTO) {
        //todo create the ability to register and await verification from admin
        return null;
    }
}