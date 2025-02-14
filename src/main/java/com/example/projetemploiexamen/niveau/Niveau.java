package com.example.projetemploiexamen.niveau;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clé primaire auto-générée

    @Column(nullable = false)
    private String name;

    @Column
    private String subjects;

    @Column
    private Long nbrStudents;

    @Column
    private Long td;
}

