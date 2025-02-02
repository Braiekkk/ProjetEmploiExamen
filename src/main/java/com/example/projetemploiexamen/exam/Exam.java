package com.example.projetemploiexamen.exam;


import com.example.projetemploiexamen.exam.DTO.CreateExamDTO;
import com.example.projetemploiexamen.exam.DTO.UpdateExamDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


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
    @Column(nullable = false)
    private LocalDateTime date; // YYYY-MM-DD HH:MM format
    @Column(nullable = false)
    private int duration;
    @Column(nullable = false)
    private String room;
    @Column(nullable = false)
    private String supervisor1;
    @Column(nullable = false)
    private String supervisor2;

    public Exam(CreateExamDTO dto) {
        this.subject = dto.getSubject();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.room = dto.getRoom();
        this.supervisor1 = dto.getSupervisor1();
        this.supervisor2 = dto.getSupervisor2();
    }

    public void updateFromDTO(UpdateExamDTO dto) {
        this.subject = dto.getSubject();
        this.date = dto.getDate();
        this.duration = dto.getDuration();
        this.room = dto.getRoom();
        this.supervisor1 = dto.getSupervisor1();
        this.supervisor2 = dto.getSupervisor2();
    }
}
