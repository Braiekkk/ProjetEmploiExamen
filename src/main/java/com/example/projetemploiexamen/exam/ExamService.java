package com.example.projetemploiexamen.exam;

import com.example.projetemploiexamen.Room.Room;
import com.example.projetemploiexamen.Room.RoomRepository;
import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.Teacher.TeacherRepository;
import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.exam.DTO.ExamDTO;
import com.example.projetemploiexamen.exam.DTO.UpdateExamDTO;
import com.example.projetemploiexamen.niveau.Niveau;
import com.example.projetemploiexamen.niveau.NiveauRepository;
import com.example.projetemploiexamen.student.StudentRepository;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;
    private final NiveauRepository niveauRepository;

    public ExamService(ExamRepository examRepository ,
                       RoomRepository roomRepository, TeacherRepository teacherRepository, NiveauRepository niveauRepository) {
        this.examRepository = examRepository;
        this.roomRepository = roomRepository;
        this.teacherRepository = teacherRepository;
        this.niveauRepository = niveauRepository;
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

    public ResponseEntity<ApiResponse<ExamDTO>> createExam(CreateExamDTO createExamDTO) {
        try {
            // Find Niveau by name and TD
            System.out.println(createExamDTO);
            Niveau niveau = niveauRepository.findByNameAndTd(createExamDTO.getNiveauName(), createExamDTO.getNiveauTd())
                    .orElseThrow(() -> new RuntimeException("Niveau not found"));

            // Create Exam object and associate with Niveau
            Exam exam = new Exam(createExamDTO, niveau);
            examRepository.save(exam);

            // Return success response
            return ResponseEntity.ok(ApiResponse.success("Exam created successfully", new ExamDTO(exam)));
        } catch (RuntimeException ex) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(ex.getMessage()));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create exam"));
        }
    }

    public ResponseEntity<ApiResponse<ExamDTO>> updateExam(Long id, UpdateExamDTO examDTO) {
        try {
            // Find the exam or throw an error if not found
            Exam exam = examRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            // Update fields only if they are provided (not null)
            if (examDTO.getSubject() != null) exam.setSubject(examDTO.getSubject());
            if (examDTO.getStartDate() != null) exam.setStartDate(examDTO.getStartDate());
            if (examDTO.getEndDate() != null) exam.setEndDate(examDTO.getEndDate());
            if (examDTO.getDuration() != null) exam.setDuration(examDTO.getDuration());

            // Handle room update
            if (examDTO.getRoomId() != null) {
                if (examDTO.getRoomId().isPresent()) {
                    Room room = roomRepository.findById(examDTO.getRoomId().get())
                            .orElseThrow(() -> new RuntimeException("Room not found with ID: " + examDTO.getRoomId().get()));
                    exam.setRoom(room);
                } else {
                    exam.setRoom(null); // Explicitly set to null if roomId is present but null
                }
            }

            // Handle supervisor update
            if (examDTO.getSupervisorIds() != null) {
                if (examDTO.getSupervisorIds().isPresent()) {
                    Set<Teacher> supervisors = new HashSet<>(teacherRepository.findAllById(examDTO.getSupervisorIds().get()));

                    // Check if any supervisor ID was not found
                    if (supervisors.size() != examDTO.getSupervisorIds().get().size()) {
                        throw new RuntimeException("One or more supervisor IDs not found.");
                    }

                    exam.setSupervisors(supervisors);
                } else {
                    exam.setSupervisors(new HashSet<>()); // If supervisorIds is present but null, clear the supervisors
                }
            }

            // Save updated exam
            examRepository.save(exam);

            // Return success response
            return ResponseEntity.ok(ApiResponse.success("Exam updated successfully", new ExamDTO(exam)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error updating exam"));
        }
    }


    public ResponseEntity<ApiResponse<ExamDTO>> deleteExam(Long id) {
        return examRepository.findById(id)
                .map(exam -> {
                    examRepository.deleteById(id);
                    return ResponseEntity.ok(ApiResponse.success("Exam deleted successfully", new ExamDTO(exam)));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Exam not found")));
    }

    public ResponseEntity<ApiResponse<List<ExamDTO>>> getAllExams() {
        List<Exam> exams = examRepository.findAll(); // Fetch all exams

        // Convert to DTOs
        List<ExamDTO> examDTOs = exams.stream()
                .map(ExamDTO::new)
                .collect(Collectors.toList());

        // Return success response
        return ResponseEntity.ok(ApiResponse.success("Exams retrieved successfully", examDTOs));
    }

    public ResponseEntity<ApiResponse<ExamDTO>> getExamById(Long id) {
        try {
            // Find exam or throw an error if not found
            Exam exam = examRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Exam not found with ID: " + id));

            // Convert to DTO and return success response
            return ResponseEntity.ok(ApiResponse.success("Exam retrieved successfully", new ExamDTO(exam)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error retrieving exam"));
        }
    }

    public ResponseEntity<ApiResponse<List<ExamDTO>>> getExamsByYearAndPeriodAndGradeAndTD( String year, String period, String grade, Long td) {
        try {
            // Find the Niveau (grade & TD)
            System.out.println(year + " " + period + " " + grade + " " + td);
            Niveau niveau = niveauRepository.findByNameAndTd(grade, td)
                    .orElseThrow(() -> new RuntimeException("Niveau not found"));

            List<Exam> exams = examRepository.getExamsByAcademicYearAndPeriodAndNiveau(year, period, niveau);

            List<ExamDTO> examDTOs = exams.stream().map(ExamDTO::new).toList();

            // Return response
            return ResponseEntity.ok(ApiResponse.success("Exams retrieved successfully", examDTOs));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error retrieving exams"));
        }
    }
}