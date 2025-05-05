package com.example.projetemploiexamen.notification.examEvent;

import com.example.projetemploiexamen.Teacher.Teacher;
import com.example.projetemploiexamen.exam.Exam;
import com.example.projetemploiexamen.notification.NotificationService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ExamUpdateListener {

    private final NotificationService notificationService;

    public ExamUpdateListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @TransactionalEventListener
    public void handleExamUpdated(ExamUpdatedEvent event) {
        Exam oldExam = event.getOldExam();
        Exam newExam = event.getUpdatedExam();

        String message = String.format("Exam '%s' has been rescheduled.\nOld time: %s → New time: %s",
                newExam.getSubject(),
                oldExam.getStartDate(),
                newExam.getStartDate());

        notificationService.sendNotificationToStudents("Exam Schedule Changed" , message);

    }
}
