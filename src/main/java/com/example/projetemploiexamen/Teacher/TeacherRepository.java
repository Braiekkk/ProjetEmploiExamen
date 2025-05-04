package com.example.projetemploiexamen.Teacher;


import com.example.projetemploiexamen.exam.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Query("SELECT t FROM Teacher t JOIN t.exams e WHERE e.id = :examId")
    List<Teacher> findByExamsContaining(@Param("examId") Long examId);

    @Query("SELECT t FROM Teacher t WHERE t.id NOT IN " +
       "(SELECT DISTINCT s.id FROM Exam e JOIN e.supervisors s WHERE :dateTime BETWEEN e.startDate AND e.endDate)")
        List<Teacher> findAvailableTeachers(@Param("dateTime") LocalDateTime dateTime);
}
