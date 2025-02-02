package com.example.projetemploiexamen.exam;


import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.exam.DTO.UpdateExamDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/exams")
public class ExamController {
    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }
    @PostMapping
    public ResponseEntity<ApiResponse<CreateExamDTO>> createExam(@RequestBody CreateExamDTO examDTO) {
        return examService.createExam(examDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateExamDTO>> updateExam(@PathVariable Long id, @RequestBody UpdateExamDTO examDTO) {
        return examService.updateExam(id, examDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteE(@PathVariable Long id) {
        return examService.deleteExam(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CreateExamDTO>>> getAllExams() {
        return examService.getAllExams();
    }
}