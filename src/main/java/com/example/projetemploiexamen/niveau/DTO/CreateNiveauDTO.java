package com.example.projetemploiexamen.niveau.DTO;


import com.example.projetemploiexamen.niveau.Niveau;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateNiveauDTO {
    private Long id; // Ajout de l'ID
    private String name;
    private String subjects;
    private Long nbrStudents;
    private Long td; // New field for number of TDs

    public CreateNiveauDTO(Niveau niveau) {
        this.id = niveau.getId(); // Assurer l'inclusion de l'ID
        this.name = niveau.getName();
        this.subjects = niveau.getSubjects();
        this.nbrStudents = niveau.getNbrStudents();
        this.td = niveau.getTd(); // Map TD field
    }
}
