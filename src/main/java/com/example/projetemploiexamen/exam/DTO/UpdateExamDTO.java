package com.example.projetemploiexamen.exam.DTO;

import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.exam.Exam;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

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
    @Nullable
    private String subject;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate; // YYYY-MM-DD HH:MM format

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;

    @Nullable
    private Long duration;

    @Nullable
    private Optional<Long> roomId; // Use Optional to check if the field exists in request

    @Nullable
    private Optional<Set<Long>> supervisorIds; // Use Optional to check if the field exists in request
}