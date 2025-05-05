package com.example.projetemploiexamen.notification;

import com.example.projetemploiexamen.notification.SSE.SseEmittersRegistry;
import com.example.projetemploiexamen.utils.ApiResponse;
import com.example.projetemploiexamen.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notifications")
public class SseController {

    private final SseEmittersRegistry emittersRegistry;
    private final JwtUtil jwtUtil;
    private final NotificationService notificationService;

    public SseController(SseEmittersRegistry emittersRegistry, JwtUtil jwtUtil, NotificationService notificationService) {
        this.emittersRegistry = emittersRegistry;
        this.jwtUtil = jwtUtil;
        this.notificationService = notificationService;
    }

    // connect to the SSE event stream
    @GetMapping("/streamStudent")
    public SseEmitter streamStudent(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header.");
        }

        // Extract the token and decode the email
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String userId = jwtUtil.extractEmail(token); // Use JwtUtil to extract email from token
        //the user id is the email of the user for the notification feature
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emittersRegistry.addEmitterStudent(userId, emitter);

        emitter.onCompletion(() -> emittersRegistry.removeEmitterStudent(userId));
        emitter.onTimeout(() -> emittersRegistry.removeEmitterStudent(userId));

        return emitter;
    }

    @GetMapping("/StreamTeacher")
    public SseEmitter streamTeacher(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header.");
        }

        // Extract the token and decode the email
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String userId = jwtUtil.extractEmail(token); // Use JwtUtil to extract email from token
        //the email is used as user id in this case
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emittersRegistry.addEmitterTeacher(userId, emitter);

        emitter.onCompletion(() -> emittersRegistry.removeEmitterTeacher(userId));
        emitter.onTimeout(() -> emittersRegistry.removeEmitterTeacher(userId));

        return emitter;
    }



    // get all previous notifications for the student
    @GetMapping("/student")
    public ResponseEntity<ApiResponse<List<Notification>> >getNotificationsStudent(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header.");
        }

        // Extract the token and decode the email
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String userId = jwtUtil.extractEmail(token);
        return notificationService.getNotificationsStudent(userId);
    }

    //get all previous notifications for the teacher
    @GetMapping("/teacher")
    public ResponseEntity<ApiResponse<List<Notification>> >getNotificationsTeacher(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization header.");
        }

        // Extract the token and decode the email
        String token = authHeader.substring(7); // Remove "Bearer " prefix
        String userId = jwtUtil.extractEmail(token);
        return notificationService.getNotificationsTeacher(userId);
    }
}
