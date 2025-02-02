package com.example.projetemploiexamen.auth;


import com.example.projetemploiexamen.admin.Admin;
import com.example.projetemploiexamen.admin.AdminRepository;
import com.example.projetemploiexamen.admin.DTO.CreateAdminDTO;
import com.example.projetemploiexamen.auth.DTO.AuthRequestDTO;
import com.example.projetemploiexamen.auth.DTO.AuthResponseDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<ApiResponse<AuthResponseDTO>> register(CreateAdminDTO createAdminDTO) {
        try {
            createAdminDTO.setPassword(passwordEncoder.encode(createAdminDTO.getPassword())); //encrypt the pasword
            Admin admin = new Admin(createAdminDTO); // create an instance of admin for database
            adminRepository.save(admin);  // saving the instance
            String token = null;
            try { token = jwtUtil.generateToken(admin.getEmail()); } catch (Exception e) {
                System.out.println("cant generate token");
                e.printStackTrace();
            }//generating token
            return ResponseEntity.ok( ApiResponse.success("admin created successfully" , new AuthResponseDTO(token))) ;
        }catch (DataIntegrityViolationException e) { // Handle email already exists case
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error("Email already registered"));
        } catch (Exception e) { // Handle unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Registration failed"));
        }
    }

    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(AuthRequestDTO request) {
        return adminRepository.findByEmail(request.getEmail())
                .filter(admin -> passwordEncoder.matches(request.getPassword(), admin.getPassword()))
                .map(admin -> {
                    String token = jwtUtil.generateToken(admin.getEmail());
                    return ResponseEntity.ok(ApiResponse.success("Login successful", new AuthResponseDTO(token)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Invalid credentials")));
    }





}