package com.example.projetemploiexamen.student;


import com.example.projetemploiexamen.student.DTO.CreateStudentDTO;
import com.example.projetemploiexamen.student.DTO.StudentDTO;
import com.example.projetemploiexamen.student.DTO.UpdateStudentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(@RequestBody CreateStudentDTO studentDTO) {
        return studentService.createStudent(studentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@PathVariable Long id, @RequestBody UpdateStudentDTO studentDTO) {
        return studentService.updateStudent(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }
}