package com.example.projetemploiexamen.exam;

import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.Teacher.TeacherRepository;
import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.exam.DTO.UpdateExamDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final TeacherRepository teacherRepository;

    public ExamService(ExamRepository examRepository,TeacherRepository teacherRepository) {
        this.examRepository = examRepository;
        this.teacherRepository = teacherRepository;
    }

    /*public ResponseEntity<ApiResponse<CreateExamDTO>> createExam(CreateExamDTO examDTO) {
        try {
            Exam exam = new Exam(examDTO);
            examRepository.save(exam);
            return ResponseEntity.ok(ApiResponse.success("Exam created successfully", new CreateExamDTO(exam)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create exam"));
        }
    }*/

    public ResponseEntity<ApiResponse<CreateExamDTO>> createExam(CreateExamDTO examDTO) {
        try {
            // Convert TeacherDTOs to Teacher entities
            Set<Teacher> supervisors = examDTO.getSupervisors().stream()
                    .map(teacherDTO -> new Teacher(teacherDTO)) // Convert TeacherDTO to Teacher entity
                    .collect(Collectors.toSet());

            // Create the Exam entity
            Exam exam = new Exam(examDTO);
            exam.setSupervisors(supervisors); // Set the supervisors (which are now Teacher entities)

            // Save the exam entity in the repository
            examRepository.save(exam);

            // Return success response with the created exam details
            return ResponseEntity.ok(ApiResponse.success("Exam created successfully", new CreateExamDTO(exam)));
        } catch (Exception e) {
            // Return error response if creation fails
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

    public ResponseEntity<ApiResponse<CreateExamDTO>> getExamById(Long id) {
        Optional<Exam> optionalExam = examRepository.findById(id);

        if (optionalExam.isPresent()) {
            Exam exam = optionalExam.get();

            // Fetch supervisors associated with this exam
            List<Teacher> supervisors = teacherRepository.findByExamsContaining(id);

            // Map the exam to DTO and set the supervisors field
            CreateExamDTO examDTO = new CreateExamDTO(exam);
            Set<TeacherDTO> supervisorDTOs = supervisors.stream()
                    .map(teacher -> new TeacherDTO(teacher)) // Convert supervisors to TeacherDTO
                    .collect(Collectors.toSet());

            examDTO.setSupervisors(supervisorDTOs); // Set the supervisors in the examDTO

            return ResponseEntity.ok(ApiResponse.success("Exam retrieved successfully", examDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Exam not found"));
        }
    }
}
