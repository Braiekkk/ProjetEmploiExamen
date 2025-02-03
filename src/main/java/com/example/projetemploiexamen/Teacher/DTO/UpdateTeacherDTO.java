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
public class UpdateTeacherDTO {
    private String name;
    private String email;
    private String DepatmentName;

    public UpdateTeacherDTO(Teacher teacher) {
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.DepatmentName = teacher.getDepartment().getName();
    }
}
