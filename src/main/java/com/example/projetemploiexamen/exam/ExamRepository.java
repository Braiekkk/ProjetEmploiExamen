package com.example.projetemploiexamen.exam;


import com.example.projetemploiexamen.niveau.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> getExamsByAcademicYearAndPeriodAndNiveau(String academicYear, String period, Niveau niveau);
}

