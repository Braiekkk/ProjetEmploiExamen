package com.example.projetemploiexamen.Room;

import com.example.projetemploiexamen.exam.Exam;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId; // Identifiant unique de la salle

    @Column(nullable = false, length = 50)
    private String roomName; // Nom ou numéro de la salle

    @Column(nullable = false)
    private Integer capacity; // Capacité de la salle

    @Column(nullable = false, length = 100)
    private String location; // Emplacement de la salle (bâtiment, étage)

    @Column(nullable = false)
    private Boolean isAvailable; // Statut de disponibilité de la salle

    // Relation Many-to-Many avec Exam
    @ManyToMany(mappedBy = "rooms") // mappedBy spécifie que la relation est gérée dans l'entité Exam
    private Set<Exam> exams;
}
