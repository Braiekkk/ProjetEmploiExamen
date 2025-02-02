package com.example.projetemploiexamen.student;

import com.example.projetemploiexamen.niveau.Niveau;
import com.example.projetemploiexamen.student.DTO.CreateStudentDTO;
import com.example.projetemploiexamen.utils.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "niveau_id", nullable = false)
    private Niveau niveau;

    public Student(CreateStudentDTO createStudentDTO,Niveau niveau) {
        this.niveau = niveau;
        this.setName(createStudentDTO.getName());
        this.setEmail(createStudentDTO.getEmail());
        this.setPassword(createStudentDTO.getPassword());
        this.setEmail(createStudentDTO.getEmail());

    }
}
