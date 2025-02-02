package com.example.projetemploiexamen.admin;


import com.example.projetemploiexamen.admin.DTO.AdminDTO;
import com.example.projetemploiexamen.admin.DTO.UpdateAdminDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

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
    // the admin after logging in, wants to check his information (jwt)
    public ResponseEntity<ApiResponse<AdminDTO>> getCurrentAdmin(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Missing or invalid token"));
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String email = jwtUtil.extractEmail(token); // Extract email from token
        return adminService.getMyAdmin(email);
    }

    @PutMapping("/me")
    //the admin after loggin in, want to edit his email/ name (jwt)
    public ResponseEntity<ApiResponse<AdminDTO>> updateCurrentAdmin(@RequestBody UpdateAdminDTO updateAdminDTO, @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Missing or invalid token"));
        }
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String email = jwtUtil.extractEmail(token); // Extract email from token
        return adminService.updateMyAdmin(email, updateAdminDTO);
    }
    //todo add the method to update password and encrypt it !!!!

}