package com.example.projetemploiexamen.Teacher;


import com.example.projetemploiexamen.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t JOIN t.exams e WHERE e.id = :examId")
    List<Teacher> findByExamsContaining(@Param("examId") Long examId);

}
