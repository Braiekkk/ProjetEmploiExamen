package com.example.projetemploiexamen.student.DTO;

import com.example.projetemploiexamen.student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStudentDTO {
    private String name;
    private String email;
    private String niveauName;

    public UpdateStudentDTO(Student student) {
        this.name = student.getName();
        this.email = student.getEmail();
        this.niveauName = student.getNiveau().getName();
    }
}