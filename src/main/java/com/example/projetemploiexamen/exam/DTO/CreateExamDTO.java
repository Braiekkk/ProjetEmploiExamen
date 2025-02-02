package com.example.projetemploiexamen.exam.DTO;


import com.example.projetemploiexamen.exam.Exam;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateExamDTO{
    private Long id;
    private String subject;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime date; // YYYY-MM-DD HH:MM format
    private int duration;
    private String room;
    private String supervisor1;
    private String supervisor2;

    public CreateExamDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = exam.getSubject();
        this.date = exam.getDate();
        this.duration = exam.getDuration();
        this.room = exam.getRoom();
        this.supervisor1 = exam.getSupervisor1();
        this.supervisor2 = exam.getSupervisor2();
    }
}
