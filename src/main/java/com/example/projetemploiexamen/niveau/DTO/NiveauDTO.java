package com.example.projetemploiexamen.niveau.DTO;

import com.example.projetemploiexamen.niveau.Niveau;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NiveauDTO {
    private String name;
    private String subjects;
    private Long nbrStudents;

    public NiveauDTO(Niveau niveau) {
        this.name = niveau.getName();
        this.subjects = niveau.getSubjects();
        this.nbrStudents = niveau.getNbrStudents();
    }
}