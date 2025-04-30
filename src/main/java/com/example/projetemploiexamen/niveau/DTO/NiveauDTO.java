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
    private long id;
    private String name;
    private String subjects;
    private Long nbrStudents;
    private Long td; // Added TD field

    public NiveauDTO(Niveau niveau) {
        this.id = niveau.getId();
        this.name = niveau.getName();
        this.subjects = niveau.getSubjects();
        this.nbrStudents = niveau.getNbrStudents();
        this.td = niveau.getTd(); // Map TD field
    }
}