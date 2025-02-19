package com.example.projetemploiexamen.Teacher;

import com.example.projetemploiexamen.Teacher.DTO.CreateTeacherDTO;
import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.DTO.UpdateTeacherDTO;
import com.example.projetemploiexamen.department.Department;
import com.example.projetemploiexamen.department.DepartmentRepository;

import com.example.projetemploiexamen.exam.DTO.ExamDTO;
import com.example.projetemploiexamen.exam.Exam;
import com.example.projetemploiexamen.exam.ExamRepository;
import com.example.projetemploiexamen.niveau.Niveau;
import com.example.projetemploiexamen.student.DTO.StudentDTO;
import com.example.projetemploiexamen.student.Student;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final ExamRepository examRepository;
    private final PasswordEncoder passwordEncoder;

    public TeacherService(TeacherRepository teacherRepository, DepartmentRepository departmentRepository,ExamRepository examRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.examRepository = examRepository;
        this.passwordEncoder= new BCryptPasswordEncoder();
    }

    public ResponseEntity<ApiResponse<TeacherDTO>> getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(teacher -> ResponseEntity.ok(ApiResponse.success("Teacher found", new TeacherDTO(teacher))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Teacher not found")));
    }

    public ResponseEntity<ApiResponse<List<TeacherDTO>>> getAllTeachers() {
        List<TeacherDTO> teachers = teacherRepository.findAll().stream()
                .map(TeacherDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("List of teachers", teachers));
    }

    public ResponseEntity<ApiResponse<TeacherDTO>> updateTeacher(Long id, UpdateTeacherDTO updateTeacherDTO) {
        try {
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            Department department = departmentRepository.findByName(updateTeacherDTO.getDepartmentName())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            teacher.setName(updateTeacherDTO.getName());
            teacher.setEmail(updateTeacherDTO.getEmail());
            teacher.setDepartment(department);
            teacherRepository.save(teacher);

            return ResponseEntity.ok(ApiResponse.success("Teacher updated successfully", new TeacherDTO(teacher)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error updating teacher"));
        }
    }

    public ResponseEntity<ApiResponse<String>> deleteTeacher(Long id) {
        return teacherRepository.findById(id)
                .map(teacher -> {
                    teacherRepository.delete(teacher);
                    return ResponseEntity.ok(ApiResponse.success("Teacher deleted successfully", "Teacher ID: " + id));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Teacher not found")));
    }

    // Méthode pour récupérer les examens après une certaine date pour un professeur
    public ResponseEntity<ApiResponse<List<ExamDTO>>> getExamsForTeacherAfterDate(Long teacherId, LocalDateTime filterDate) {
        // Find the teacher by ID
        Teacher teacher = teacherRepository.findById(teacherId).orElse(null);

        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Teacher not found"));
        }

        // Find all exams supervised by the teacher after the specified date
        List<Exam> exams = examRepository.findBySupervisorsContainsAndDateAfter(teacher, filterDate);

        // Map the Exam entities to ExamDTO
        List<ExamDTO> examDTOs = exams.stream()
                .map(exam -> new ExamDTO(exam)) // Convert each Exam to ExamDTO
                .collect(Collectors.toList());

        // Return the list of ExamDTOs in the response
        return ResponseEntity.ok(ApiResponse.success("Exams retrieved successfully", examDTOs));
    }


    public ResponseEntity<ApiResponse<TeacherDTO>> addTeacher(CreateTeacherDTO createTeacherDTO) {
        try {

            createTeacherDTO.setPassword(passwordEncoder.encode(createTeacherDTO.getPassword()));
            Department department = departmentRepository.findByName(createTeacherDTO.getDepartmentName())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            Teacher teacher = new Teacher(createTeacherDTO, department);
            teacherRepository.save(teacher);

            return ResponseEntity.ok(ApiResponse.success("Teacher created successfully", new TeacherDTO(teacher)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error creating student"));
        }
    }
}

