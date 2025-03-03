package com.example.projetemploiexamen.niveau;

import com.example.projetemploiexamen.exam.Exam;
import com.example.projetemploiexamen.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @OneToMany(mappedBy = "niveau", cascade = CascadeType.PERSIST)
    private List<Exam> exams;


}
