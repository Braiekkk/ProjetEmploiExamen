package com.example.projetemploiexamen.exam.DTO;

import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.Teacher.Teacher;
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
public class CreateExamDTO {

    private Long id;
    private String subject;
    private LocalDateTime date;
    private int duration;
    private String room;

    // Change to Set<TeacherDTO>
    private Set<TeacherDTO> supervisors;

    // Constructor to convert Exam entity to CreateExamDTO
    public CreateExamDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = exam.getSubject();
        this.date = exam.getDate();
        this.duration = exam.getDuration();
        this.room = exam.getRoom();
        // Convert the Set<Teacher> to Set<TeacherDTO>
        this.supervisors = exam.getSupervisors().stream()
                .map(teacher -> new TeacherDTO(teacher)) // Convert Teacher to TeacherDTO
                .collect(Collectors.toSet());
    }
}
