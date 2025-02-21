package com.example.projetemploiexamen.Teacher.DTO;

import com.example.projetemploiexamen.Teacher.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherDTO {
    private Long id;
    private String name;
    private String email;
    private String departmentName;

    public TeacherDTO(Teacher teacher) {
        this.id= teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.departmentName = teacher.getDepartment().getName();
    }
}
