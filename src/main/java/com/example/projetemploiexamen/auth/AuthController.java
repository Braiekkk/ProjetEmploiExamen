package com.example.projetemploiexamen.auth;


import com.example.projetemploiexamen.admin.Admin;
import com.example.projetemploiexamen.admin.AdminService;
import com.example.projetemploiexamen.admin.DTO.CreateAdminDTO;
import com.example.projetemploiexamen.auth.DTO.AuthResponseDTO;
import com.example.projetemploiexamen.auth.DTO.AuthRequestDTO;
import com.example.projetemploiexamen.student.DTO.CreateStudentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/admin/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> adminRegister(@RequestBody CreateAdminDTO dto) {
        return authService.registerAdmin(dto);
        //todo implement an acceptance logic when a new admin is trying to create an account
    }

    @PostMapping("/auth/admin/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> adminLogin(@RequestBody AuthRequestDTO authRequest) {
        return authService.loginAdmin(authRequest);
    }

    @PostMapping("/auth/student/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> studentLogin(@RequestBody AuthRequestDTO authRequest) {
        return authService.loginStudent(authRequest);
    }
    @PostMapping("/auth/student/register")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> studentRegister(@RequestBody CreateStudentDTO authRequest) {
        //todo the student trying to create an account (awaiting verification)
        return authService.registerStudent(authRequest);
    }
}