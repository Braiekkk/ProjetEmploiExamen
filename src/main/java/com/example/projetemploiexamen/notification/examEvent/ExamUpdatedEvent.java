package com.example.projetemploiexamen.notification.examEvent;

import com.example.projetemploiexamen.exam.Exam;

public class ExamUpdatedEvent {
    private final Exam oldExam;
    private final Exam updatedExam;

    public ExamUpdatedEvent(Exam oldExam, Exam updatedExam) {
        this.oldExam = oldExam;
        this.updatedExam = updatedExam;
    }

    public Exam getOldExam() {
        return oldExam;
    }

    public Exam getUpdatedExam() {
        return updatedExam;
    }
}
