package com.example.projetemploiexamen.student;

import com.example.projetemploiexamen.niveau.Niveau;
import com.example.projetemploiexamen.niveau.NiveauRepository;
import com.example.projetemploiexamen.student.DTO.CreateStudentDTO;
import com.example.projetemploiexamen.student.DTO.StudentDTO;
import com.example.projetemploiexamen.student.DTO.UpdateStudentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final NiveauRepository niveauRepository;

    public StudentService(StudentRepository studentRepository, NiveauRepository niveauRepository) {
        this.studentRepository = studentRepository;
        this.niveauRepository = niveauRepository;
    }

    public ResponseEntity<ApiResponse<StudentDTO>> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(student -> ResponseEntity.ok(ApiResponse.success("Student found", new StudentDTO(student))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Student not found")));
    }

    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents() {
        List<StudentDTO> students = studentRepository.findAll().stream()
                .map(StudentDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("List of students", students));
    }

    public ResponseEntity<ApiResponse<StudentDTO>> createStudent(CreateStudentDTO createStudentDTO) {
        try {
            System.out.println("CreateStudentDTO: " + createStudentDTO.toString() );

            Niveau niveau = niveauRepository.findByName(createStudentDTO.getNiveauName())
                    .orElseThrow(() -> new RuntimeException("Niveau not found"));
            //todo hash or generate the student password !!!!!!
            Student student = new Student(createStudentDTO, niveau);
            studentRepository.save(student);

            return ResponseEntity.ok(ApiResponse.success("Student created successfully", new StudentDTO(student)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error creating student"));
        }
    }

    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(Long id, UpdateStudentDTO updateStudentDTO) {
        try {
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Niveau niveau = niveauRepository.findByName(updateStudentDTO.getNiveauName())
                    .orElseThrow(() -> new RuntimeException("Niveau not found"));

            student.setNiveau(niveau);
            student.setName(updateStudentDTO.getName());
            student.setEmail(updateStudentDTO.getEmail());
            studentRepository.save(student);

            return ResponseEntity.ok(ApiResponse.success("Student updated successfully", new StudentDTO(student)));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error updating student"));
        }
    }

    public ResponseEntity<ApiResponse<String>> deleteStudent(Long id) {
        return studentRepository.findById(id)
                .map(student -> {
                    studentRepository.delete(student);
                    return ResponseEntity.ok(ApiResponse.success("Student deleted successfully", "Student ID: " + id));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Student not found")));
    }
}
