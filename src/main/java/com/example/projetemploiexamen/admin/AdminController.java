package com.example.projetemploiexamen.admin;


import com.example.projetemploiexamen.admin.DTO.AdminDTO;
import com.example.projetemploiexamen.admin.DTO.UpdateAdminDTO;
import com.example.projetemploiexamen.admin.DTO.UpdatePasswordAdminDTO;
import com.example.projetemploiexamen.auth.DTO.AuthResponseDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.lang.annotation.Documented;
import java.util.List;

    @RestController
    @RequestMapping("/admin")
    public class AdminController {

        private final AdminService adminService;
        private final JwtUtil jwtUtil;

        public AdminController(AdminService adminService, JwtUtil jwtUtil) {
            this.adminService = adminService;
            this.jwtUtil = jwtUtil;
        }

        @GetMapping("/me")
        public ResponseEntity<ApiResponse<AdminDTO>> getCurrentAdmin(@RequestHeader("Authorization") String authHeader) {
            String email = extractEmailFromToken(authHeader);
            return adminService.getMyAdmin(email);
        }

        @PutMapping("/me")
        public ResponseEntity<ApiResponse<AuthResponseDTO>> updateCurrentAdmin(@RequestBody UpdateAdminDTO updateAdminDTO, @RequestHeader("Authorization") String authHeader) {
            String email = extractEmailFromToken(authHeader);
            return adminService.updateMyAdmin(email, updateAdminDTO);
        }
        @PostMapping("/me/updatepassword")
        public ResponseEntity<ApiResponse<AdminDTO>> updatePasswordCurrentAdmin(@RequestBody UpdatePasswordAdminDTO updatePasswordAdminDTO, @RequestHeader("Authorization") String authHeader) {
        return null ;
        }//todo add the method to update password and encrypt it !!!!







        private String extractEmailFromToken(String authHeader) {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header.");
            }
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            return jwtUtil.extractEmail(token); // Extract email from JWT
        }

    }