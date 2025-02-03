package com.example.projetemploiexamen.Teacher;

import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.DTO.UpdateTeacherDTO;
import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.Teacher.TeacherRepository;
import com.example.projetemploiexamen.department.Department;
import com.example.projetemploiexamen.department.DepartmentRepository;
import com.example.projetemploiexamen.department.Department;

import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;

    public TeacherService(TeacherRepository teacherRepository, DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
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

            Department department = (Department) departmentRepository.findByName(updateTeacherDTO.getDepatmentName())
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            teacher.setDepartmentId(Long.valueOf(department.getDepartment_id()));
            teacher.setName(updateTeacherDTO.getName());
            teacher.setEmail(updateTeacherDTO.getEmail());
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
}

