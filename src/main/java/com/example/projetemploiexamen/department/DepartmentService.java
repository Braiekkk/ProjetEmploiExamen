package com.example.projetemploiexamen.department;


import com.example.projetemploiexamen.department.DTO.CreateDepartmentDTO;
import com.example.projetemploiexamen.department.DTO.UpdateDepartmentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<ApiResponse<Department>> getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(department -> ResponseEntity.ok(ApiResponse.success("Department found", department)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Department not found")));
    }

    public ResponseEntity<ApiResponse<List<Department>>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("List of departments", departments));
    }

    public ResponseEntity<ApiResponse<Department>> createDepartment(CreateDepartmentDTO createDepartmentDTO) {
        try {
            Department department = new Department();
            department.setName(createDepartmentDTO.getName());

            System.out.println("created department : " + department);
            departmentRepository.save(department);
            return ResponseEntity.ok(ApiResponse.success("Department created successfully", department));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error creating department"));
        }
    }

    public ResponseEntity<ApiResponse<Department>> updateDepartment(Long id, UpdateDepartmentDTO updateDepartmentDTO) {
        try {
            Department department = departmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Department not found"));

            department.setName(updateDepartmentDTO.getName());
            department.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            departmentRepository.save(department);
            return ResponseEntity.ok(ApiResponse.success("Department updated successfully", department));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error("Error updating department"));
        }
    }

    public ResponseEntity<ApiResponse<String>> deleteDepartment(Long id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    departmentRepository.delete(department);
                    return ResponseEntity.ok(ApiResponse.success("Department deleted successfully", "Department ID: " + id));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Department not found")));
    }
}
