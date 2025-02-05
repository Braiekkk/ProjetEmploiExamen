package com.example.projetemploiexamen.admin;

import com.example.projetemploiexamen.admin.DTO.AdminDTO;
import com.example.projetemploiexamen.admin.DTO.UpdateAdminDTO;
import com.example.projetemploiexamen.auth.DTO.AuthResponseDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;
    public AdminService(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<ApiResponse<AdminDTO>> getMyAdmin(String email) {
        return adminRepository.findByEmail(email)
                .map(admin -> ResponseEntity.ok(ApiResponse.success("Admin found", new AdminDTO(admin))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Admin not found")));
    }

    public ResponseEntity<ApiResponse<AuthResponseDTO>> updateMyAdmin(String email, UpdateAdminDTO updateAdminDTO) {
        return adminRepository.findByEmail(email)
                .map(admin -> {
                    admin.setName(updateAdminDTO.getName());
                    admin.setEmail(updateAdminDTO.getEmail());
                    adminRepository.save(admin);
                    String token = jwtUtil.generateToken(updateAdminDTO.getEmail(), "ADMIN");
                    return ResponseEntity.ok(ApiResponse.success("Admin updated successfully", new AuthResponseDTO(token)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Admin not found")));
    }


    //todo resetPassword
}