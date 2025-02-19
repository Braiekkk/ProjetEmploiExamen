package com.example.projetemploiexamen.department.DTO;


import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.department.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private long id;
    private String name;// Nom du département
    private List<TeacherDTO> teachers;
    //private String head;        // Nom du chef du département (ou ID si nécessaire)

    public DepartmentDTO(Department departement) {
        this.id=departement.getDepartment_id();
        this.name = departement.getName();
        this.teachers = departement.getTeachers().size()!=0 ? departement.getTeachers().stream().map(TeacherDTO::new).collect(Collectors.toList()): new ArrayList<>();

       // ToDo : Chef department
    }
}
