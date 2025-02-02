package com.example.projetemploiexamen.student.DTO;

import com.example.projetemploiexamen.niveau.DTO.NiveauDTO;
import com.example.projetemploiexamen.student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private NiveauDTO niveau;

    public StudentDTO(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.email = student.getEmail();
        this.niveau = new NiveauDTO(student.getNiveau());
    }
}
