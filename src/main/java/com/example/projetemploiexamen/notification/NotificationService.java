package com.example.projetemploiexamen.notification;

import com.example.projetemploiexamen.notification.SSE.SseEmittersRegistry;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SseEmittersRegistry emittersRegistry;
    private final ObjectMapper objectMapper;

    public NotificationService(NotificationRepository notificationRepository,
                               SseEmittersRegistry emittersRegistry,
                               ObjectMapper objectMapper) {
        this.notificationRepository = notificationRepository;
        this.emittersRegistry = emittersRegistry;
        this.objectMapper = objectMapper;
    }

    public void sendNotificationToStudents(String title, String message) {
        LocalDateTime now = LocalDateTime.now();

        emittersRegistry.getAllEmitters().forEach((userId, emitter) -> {
            Notification notification = new Notification();
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setTimestamp(now);
            notification.setUserId(userId);

            try {
                String json = objectMapper.writeValueAsString(notification); // ✅ serialize to JSON
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(json));
                notification.setSeen(true);
            } catch (IOException e) {
                emitter.complete();
                emittersRegistry.removeEmitterStudent(userId);
                notification.setSeen(false);
            }

            notificationRepository.save(notification);
        });
        System.out.println("Active emitters: " + emittersRegistry.getAllEmitters().size());

    }

    public ResponseEntity<ApiResponse<List<Notification>>> getNotificationsStudent(String userId) {

        List<Notification> notifications = notificationRepository.findByUserId(userId + "_student");
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));

    }

    public ResponseEntity<ApiResponse<List<Notification>>> getNotificationsTeacher(String userId) {

        List<Notification> notifications = notificationRepository.findByUserId(userId + "_teacher");
        return ResponseEntity.ok(ApiResponse.success("Notifications retrieved successfully", notifications));

    }
}
