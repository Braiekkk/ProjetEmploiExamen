package com.example.projetemploiexamen.exam;

import com.example.projetemploiexamen.Room.Room;
import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.exam.DTO.UpdateExamDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(nullable = false)
    private LocalDateTime date; // YYYY-MM-DD HH:MM format

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private String room;

    // Relation Many-to-Many avec Teacher
    @ManyToMany
    @JoinTable(
            name = "exam_supervisors", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "exam_id"), // Colonne qui fait référence à l'examen
            inverseJoinColumns = @JoinColumn(name = "teacher_id") // Colonne qui fait référence à l'enseignant
    )
    private Set<Teacher> supervisors;

    // Relation Many-to-Many avec Room
    @ManyToMany
    @JoinTable(
            name = "exam_rooms", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "exam_id"), // Colonne qui fait référence à l'examen
            inverseJoinColumns = @JoinColumn(name = "room_id") // Colonne qui fait référence à la salle
    )
    private Set<Room> rooms;

    public Exam(CreateExamDTO dto) {
        this.subject = dto.getSubject();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.room = dto.getRoom();
        this.supervisors = dto.getSupervisors().stream()
                .map(teacherDTO -> new Teacher(teacherDTO)) // Convert TeacherDTO to Teacher
                .collect(Collectors.toSet()); // List des superviseurs envoyée par DTO
    }

    public void updateFromDTO(UpdateExamDTO dto) {
        this.subject = dto.getSubject();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.room = dto.getRoom();
        this.supervisors = dto.getSupervisors(); // List des superviseurs envoyée par DTO
    }
}
