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
    private String name;
    private String email;

    public TeacherDTO(Teacher teacher) {
        this.name = teacher.getName();
        this.email = teacher.getEmail();
    }
}
