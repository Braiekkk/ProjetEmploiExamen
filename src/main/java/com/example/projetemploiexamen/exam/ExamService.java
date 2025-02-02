package com.example.projetemploiexamen.exam;

import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.exam.DTO.UpdateExamDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }


    public ResponseEntity<ApiResponse<CreateExamDTO>> createExam(CreateExamDTO examDTO) {
        try {
            Exam exam = new Exam(examDTO);
            examRepository.save(exam);
            return ResponseEntity.ok(ApiResponse.success("Exam created successfully", new CreateExamDTO(exam)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create exam"));
        }
    }

    public ResponseEntity<ApiResponse<UpdateExamDTO>> updateExam(Long id, UpdateExamDTO examDTO) {
        return examRepository.findById(id)
                .map(exam -> {
                    exam.updateFromDTO(examDTO);
                    examRepository.save(exam);
                    return ResponseEntity.ok(ApiResponse.success("Exam updated successfully", new UpdateExamDTO(exam)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Exam not found")));
    }

    public ResponseEntity<ApiResponse<String>> deleteExam(Long id) {
        return examRepository.findById(id)
                .map(exam -> {
                    examRepository.deleteById(id);
                    return ResponseEntity.ok(ApiResponse.success("Exam deleted successfully", "Exam ID: " + id));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Exam not found")));
    }

    public ResponseEntity<ApiResponse<List<CreateExamDTO>>> getAllExams() {
        List<CreateExamDTO> exams = examRepository.findAll().stream()
                .map(CreateExamDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("List of all exams", exams));
    }
}
