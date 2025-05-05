package com.example.projetemploiexamen.notification;

import com.example.projetemploiexamen.utils.ApiResponse;
import jakarta.persistence.Column;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping("/sendNotificationToStudents")
    public ResponseEntity<ApiResponse<String>> sendNotification() {
        //todo implement logic for generatin notifs for now the notification is just " this is a test notification"
        String notificationTitle = "Test Notification";
        String notificationMessage = "This is a test notification";

        notificationService.sendNotificationToStudents(notificationTitle, notificationMessage);
        return ResponseEntity.ok (ApiResponse.success("Notification sent successfully", null ));
    }
}
