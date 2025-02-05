package com.example.projetemploiexamen.student;

import com.example.projetemploiexamen.student.DTO.CreateStudentDTO;
import com.example.projetemploiexamen.student.DTO.StudentDTO;
import com.example.projetemploiexamen.student.DTO.UpdateStudentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("/student")
public class StudentControllerSelf {

    private final StudentService studentService;
    private final JwtUtil jwtUtil;

    public StudentControllerSelf(StudentService studentService, JwtUtil jwtUtil) {
        this.studentService = studentService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<StudentDTO>> getCurrentStudent(@RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        return studentService.getStudentByEmail(email);
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<StudentDTO>> updateCurrentStudent(@RequestBody UpdateStudentDTO studentDTO, @RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        return studentService.updateStudentByEmail(email, studentDTO);
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<String>> deleteCurrentStudent(@RequestHeader("Authorization") String authHeader) {
        String email = extractEmailFromToken(authHeader);
        return studentService.deleteStudentByEmail(email);
    }

    // ✅ Extract email from JWT token
    private String extractEmailFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header.");
        }
        String token = authHeader.substring(7);
        return jwtUtil.extractEmail(token);
    }
}