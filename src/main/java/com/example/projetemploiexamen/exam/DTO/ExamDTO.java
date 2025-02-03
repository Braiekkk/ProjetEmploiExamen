package com.example.projetemploiexamen.exam.DTO;

import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.exam.Exam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamDTO {
    private String subject;
    private LocalDateTime date;
    private int duration;
    private String room;

    // If you have multiple supervisors, you can include them as well
    private Set<TeacherDTO> supervisors;

    // Constructor to convert an `Exam` into an `ExamDTO`
    public ExamDTO(Exam exam) {
        this.subject = exam.getSubject();
        this.date = exam.getDate();
        this.duration = exam.getDuration();
        this.room = exam.getRoom();
        this.supervisors = exam.getSupervisors().stream()
                .map(teacher -> new TeacherDTO(teacher)) // Map each supervisor to a DTO
                .collect(Collectors.toSet());
    }
}

