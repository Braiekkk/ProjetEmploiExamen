package com.example.projetemploiexamen.notification.SSE;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmittersRegistry {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    private String formatKey(String userId, String role) {
        return userId + "_" + role.toLowerCase();
    }

    public void addEmitterStudent(String userId, SseEmitter emitter) {
        String key = formatKey(userId, "student");
        replaceEmitter(key, emitter);
    }

    public void removeEmitterStudent(String userId) {
        String key = formatKey(userId, "student");
        emitters.remove(key);
    }

    public void addEmitterTeacher(String userId, SseEmitter emitter) {
        String key = formatKey(userId, "teacher");
        replaceEmitter(key, emitter);
    }

    public void removeEmitterTeacher(String userId) {
        String key = formatKey(userId, "teacher");
        emitters.remove(key);
    }

    public void addEmitter(String userId, SseEmitter emitter) {
        replaceEmitter(userId, emitter);
    }

    private void replaceEmitter(String key, SseEmitter emitter) {
        SseEmitter existing = emitters.get(key);
        if (existing != null) {
            existing.complete(); // Close any previous emitter
        }
        emitters.put(key, emitter);
    }

    public Map<String, SseEmitter> getAllEmitters() {
        return emitters;
    }
}
