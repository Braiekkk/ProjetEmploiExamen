package com.example.projetemploiexamen.niveau;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Niveau {
    @Id
    private String name;
    @Column
    private String subjects;
    @Column
    private Long nbrStudents;
}
