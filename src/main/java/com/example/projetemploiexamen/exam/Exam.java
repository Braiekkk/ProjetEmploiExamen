package com.example.projetemploiexamen.exam;

import com.example.projetemploiexamen.Room.Room;
import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.niveau.Niveau;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String subject;

    @Column
    private String period;

    @Column
    private String academicYear;

    @Column
    private Long duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate; // YYYY-MM-DD HH:MM format

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate; // YYYY-MM-DD HH:MM format

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="niveau_id")
    private Niveau niveau;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "exam_supervisors",
            joinColumns = @JoinColumn(name = "exam_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> supervisors;

    public Exam(CreateExamDTO createExamDTO, Niveau niveau) {
        this.subject = createExamDTO.getSubject();
        this.period = createExamDTO.getPeriod();
        this.academicYear = createExamDTO.getAcademicYear();
        this.duration = createExamDTO.getDuration();
        this.startDate = createExamDTO.getStartDate();
        this.endDate = createExamDTO.getEndDate();
        this.niveau = niveau;
    }
}
