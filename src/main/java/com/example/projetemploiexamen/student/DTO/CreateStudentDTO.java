package com.example.projetemploiexamen.student.DTO;

import com.example.projetemploiexamen.niveau.DTO.NiveauDTO;
import com.example.projetemploiexamen.student.Student;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateStudentDTO {
    private String name;
    private String email;
    private String password;
    private String niveauName;

    public CreateStudentDTO(Student student) {
        this.name = student.getName();
        this.email = student.getEmail();
        this.niveauName = student.getNiveau().getName();
        this.password = student.getPassword();
    }
}