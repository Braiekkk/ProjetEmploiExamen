package com.example.projetemploiexamen.Teacher;

import com.example.projetemploiexamen.Teacher.DTO.CreateTeacherDTO;
import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.DTO.UpdateTeacherDTO;
import com.example.projetemploiexamen.exam.DTO.ExamDTO;
import com.example.projetemploiexamen.exam.Exam;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/admin/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacher(@PathVariable Long id, @RequestBody UpdateTeacherDTO teacherDTO) {
        return teacherService.updateTeacher(id, teacherDTO);
    }
    @PostMapping
    public ResponseEntity<ApiResponse<TeacherDTO>> addTeacher(@RequestBody CreateTeacherDTO createTeacherDTO) {
        return this.teacherService.addTeacher(createTeacherDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTeacher(@PathVariable Long id) {
        return teacherService.deleteTeacher(id);
    }

    @GetMapping("/{id}/exams")
    public ResponseEntity<ApiResponse<List<ExamDTO>>> getExamsForTeacher(@PathVariable Long id) {
        return teacherService.getExamsForTeacher(id);
    }

}
