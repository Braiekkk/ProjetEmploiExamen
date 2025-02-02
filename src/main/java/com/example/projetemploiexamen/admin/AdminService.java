package com.example.projetemploiexamen.admin;

import com.example.projetemploiexamen.admin.DTO.AdminDTO;
import com.example.projetemploiexamen.admin.DTO.UpdateAdminDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public ResponseEntity<ApiResponse<AdminDTO>> getMyAdmin(String email) {
        return adminRepository.findByEmail(email)
                .map(admin -> ResponseEntity.ok(ApiResponse.success("Admin found", new AdminDTO(admin))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Admin not found")));
    }

    public ResponseEntity<ApiResponse<AdminDTO>> updateMyAdmin(String email, UpdateAdminDTO updateAdminDTO) {
        return adminRepository.findByEmail(email)
                .map(admin -> {
                    admin.setName(updateAdminDTO.getName());
                    admin.setEmail(updateAdminDTO.getEmail());
                    adminRepository.save(admin);
                    //todo: after updating information resend an updated token
                    return ResponseEntity.ok(ApiResponse.success("Admin updated successfully", new AdminDTO(admin)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Admin not found")));
    }
    //todo resetPassword
}