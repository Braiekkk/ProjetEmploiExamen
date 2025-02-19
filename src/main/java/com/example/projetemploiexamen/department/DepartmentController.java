package com.example.projetemploiexamen.department;



import com.example.projetemploiexamen.department.DTO.CreateDepartmentDTO;
import com.example.projetemploiexamen.department.DTO.DepartmentDTO;
import com.example.projetemploiexamen.department.DTO.UpdateDepartmentDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DepartmentDTO>> getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentDTO>>> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DepartmentDTO>> createDepartment(@RequestBody CreateDepartmentDTO departmentDTO) {
        return departmentService.createDepartment(departmentDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Department>> updateDepartment(@PathVariable Long id, @RequestBody UpdateDepartmentDTO departmentDTO) {
        return departmentService.updateDepartment(id, departmentDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }
}
