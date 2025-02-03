package com.example.projetemploiexamen.exam.DTO;

import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.exam.Exam;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateExamDTO {

    private Long id;
    private String subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date; // YYYY-MM-DD HH:MM format

    private int duration;
    private String room;

    // Liste des superviseurs (Many-to-Many relation)
    private Set<Teacher> supervisors;

    public UpdateExamDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = exam.getSubject();
        this.date = exam.getDate();
        this.duration = exam.getDuration();
        this.room = exam.getRoom();
        this.supervisors = exam.getSupervisors(); // Liste des superviseurs
    }
}
