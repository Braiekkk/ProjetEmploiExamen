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
import com.example.projetemploiexamen.notification.examEvent.ExamUpdatedEvent;
import com.example.projetemploiexamen.student.StudentRepository;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final RoomRepository roomRepository;
    private final TeacherRepository teacherRepository;
    private final NiveauRepository niveauRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ExamService(ExamRepository examRepository ,
                       RoomRepository roomRepository, TeacherRepository teacherRepository, NiveauRepository niveauRepository, ApplicationEventPublisher eventPublisher) {
        this.examRepository = examRepository;
        this.roomRepository = roomRepository;
        this.teacherRepository = teacherRepository;
        this.niveauRepository = niveauRepository;
        this.eventPublisher = eventPublisher;
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
    @Transactional
    public ResponseEntity<ApiResponse<ExamDTO>> updateExam(Long id, UpdateExamDTO examDTO) {
        try {
            // Find the exam or throw an error if not found
            Exam oldExam = examRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            // Create a clone of the old exam for event publishing
            Exam clonedOld = cloneExam(oldExam);

            // Check if schedule-relevant fields or supervisors changed
            boolean changed =
                    (examDTO.getStartDate() != null && !Objects.equals(oldExam.getStartDate(), examDTO.getStartDate())) ||
                    (examDTO.getEndDate() != null && !Objects.equals(oldExam.getEndDate(), examDTO.getEndDate())) ||
                    (examDTO.getRoomId() != null && examDTO.getRoomId().isPresent() && !Objects.equals(oldExam.getRoom() != null ? oldExam.getRoom().getId() : null, examDTO.getRoomId().get())) ||
                    (examDTO.getSupervisorIds() != null && examDTO.getSupervisorIds().isPresent() &&
                            !Objects.equals(
                                    oldExam.getSupervisors().stream().map(Teacher::getId).collect(Collectors.toSet()),
                                    new HashSet<>(examDTO.getSupervisorIds().get())
                            ));
            // Update fields only if they are provided (not null)
            if (examDTO.getSubject() != null) oldExam.setSubject(examDTO.getSubject());
            if (examDTO.getStartDate() != null) oldExam.setStartDate(examDTO.getStartDate());
            if (examDTO.getEndDate() != null) oldExam.setEndDate(examDTO.getEndDate());
            if (examDTO.getDuration() != null) oldExam.setDuration(examDTO.getDuration());

            // Handle room update
            if (examDTO.getRoomId() != null) {
                if (examDTO.getRoomId().isPresent()) {
                    Room room = roomRepository.findById(examDTO.getRoomId().get())
                            .orElseThrow(() -> new RuntimeException("Room not found with ID: " + examDTO.getRoomId().get()));
                    oldExam.setRoom(room);
                } else {
                    oldExam.setRoom(null); // Explicitly set to null if roomId is present but null
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

                    oldExam.setSupervisors(supervisors);
                } else {
                    oldExam.setSupervisors(new HashSet<>()); // If supervisorIds is present but null, clear the supervisors
                }
            }

            // Save updated exam
            examRepository.save(oldExam);

            // Publish event if relevant fields changed
            if (changed) {
                eventPublisher.publishEvent(new ExamUpdatedEvent(clonedOld, oldExam));
            }

            // Return success response
            return ResponseEntity.ok(ApiResponse.success("Exam updated successfully", new ExamDTO(oldExam)));

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

    private void updateFields(Exam target, Exam source) {
        target.setStartDate(source.getStartDate());
        target.setEndDate(source.getEndDate());
        target.setRoom(source.getRoom());
        target.setDuration(source.getDuration());
        target.setSubject(source.getSubject());
    }

    private Exam cloneExam(Exam exam) {
        Exam copy = new Exam();
        copy.setId(exam.getId());
        copy.setStartDate(exam.getStartDate());
        copy.setEndDate(exam.getEndDate());
        copy.setRoom(exam.getRoom());
        copy.setDuration(exam.getDuration());
        copy.setSubject(exam.getSubject());
        copy.setSupervisors(new HashSet<>(exam.getSupervisors()));
        return copy;
    }
}