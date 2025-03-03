package com.example.projetemploiexamen.exam.DTO;

import com.example.projetemploiexamen.Room.DTO.RoomDTO;
import com.example.projetemploiexamen.Teacher.DTO.TeacherDTO;
import com.example.projetemploiexamen.exam.Exam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamDTO {
    private long id;
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String period;
    private String academicYear;
    private Long duration;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String niveauName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long niveauTd ;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) // Excludes empty lists
    private Set<TeacherDTO> supervisors;
    @JsonInclude(JsonInclude.Include.NON_NULL) // Excludes null values
    private RoomDTO room;

    // Response body of the exam get request
    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.subject = exam.getSubject();
        this.startDate = exam.getStartDate();
        this.endDate = exam.getEndDate();
        this.duration = exam.getDuration();
        this.period = exam.getPeriod();
        this.academicYear = exam.getAcademicYear();

        // Convert supervisors list, exclude if empty
        this.supervisors = (exam.getSupervisors() == null || exam.getSupervisors().isEmpty())
                ? new HashSet<>()
                : exam.getSupervisors().stream().map(TeacherDTO::new).collect(Collectors.toSet());

        // Convert room, exclude if null
        this.room = (exam.getRoom() != null) ? new RoomDTO(exam.getRoom()) : null;
        this.niveauName= (exam.getNiveau() != null) ? exam.getNiveau().getName() : null;
        this.niveauTd= (exam.getNiveau() != null) ? exam.getNiveau().getTd() : null;
    }
}
