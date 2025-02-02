package com.example.projetemploiexamen.auth;


import com.example.projetemploiexamen.admin.Admin;
import com.example.projetemploiexamen.admin.DTO.CreateAdminDTO;
import com.example.projetemploiexamen.auth.DTO.AuthResponseDTO;
import com.example.projetemploiexamen.auth.DTO.AuthRequestDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public  ResponseEntity<ApiResponse<AuthResponseDTO>> register(@RequestBody CreateAdminDTO dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@RequestBody AuthRequestDTO request) {
        return authService.login(request);
    }
}