package com.example.projetemploiexamen.exam;

import com.example.projetemploiexamen.Teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findBySupervisorsContainsAndDateAfter(Teacher teacher, LocalDateTime date);
}

