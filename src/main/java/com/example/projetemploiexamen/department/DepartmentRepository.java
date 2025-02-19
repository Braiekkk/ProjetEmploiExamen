package com.example.projetemploiexamen.department;

import com.example.projetemploiexamen.department.DTO.DepartmentDTO;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

}
