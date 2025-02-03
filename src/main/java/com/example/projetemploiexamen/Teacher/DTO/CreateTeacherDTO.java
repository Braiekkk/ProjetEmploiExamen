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
public class CreateTeacherDTO {
    private String name;
    private String email;
    private String password;
    private String department;

    public CreateTeacherDTO(Teacher teacher) {
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.password = teacher.getPassword();
        this.department = teacher.getDepartment().getName();
    }
}
