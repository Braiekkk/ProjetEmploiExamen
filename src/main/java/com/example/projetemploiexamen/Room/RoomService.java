package com.example.projetemploiexamen.Room;

import com.example.projetemploiexamen.exam.Exam;
import com.example.projetemploiexamen.exam.ExamRepository;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final ExamRepository examRepository;

    public RoomService(RoomRepository roomRepository, ExamRepository examRepository) {
        this.roomRepository = roomRepository;
        this.examRepository = examRepository;
    }

    // Create a new room
    public ResponseEntity<ApiResponse<Room>> createRoom(Room room) {
        try {
            roomRepository.save(room);
            return ResponseEntity.ok(ApiResponse.success("Room created successfully", room));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create room"));
        }
    }

    // Update room details
    public ResponseEntity<ApiResponse<Room>> updateRoom(Long id, Room roomDetails) {
        Optional<Room> existingRoom = roomRepository.findById(id);
        if (existingRoom.isPresent()) {
            Room room = existingRoom.get();
            room.setRoomName(roomDetails.getRoomName());
            room.setCapacity(roomDetails.getCapacity());
            room.setLocation(roomDetails.getLocation());
            room.setIsAvailable(roomDetails.getIsAvailable());
            roomRepository.save(room);
            return ResponseEntity.ok(ApiResponse.success("Room updated successfully", room));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Room not found"));
        }
    }

    // Delete a room
    public ResponseEntity<ApiResponse<String>> deleteRoom(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            roomRepository.deleteById(id);
            return ResponseEntity.ok(ApiResponse.success("Room deleted successfully", "Room ID: " + id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Room not found"));
        }
    }

    // Get all rooms
    public ResponseEntity<ApiResponse<List<Room>>> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return ResponseEntity.ok(ApiResponse.success("List of all rooms", rooms));
    }

    // Get a room by its ID
    public ResponseEntity<ApiResponse<Room>> getRoomById(Long id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("Room retrieved successfully", room.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Room not found"));
        }
    }

    // Get all available rooms
    public ResponseEntity<ApiResponse<List<Room>>> getAvailableRooms() {
        List<Room> availableRooms = roomRepository.findByIsAvailable(true);
        return ResponseEntity.ok(ApiResponse.success("List of available rooms", availableRooms));
    }

    public ResponseEntity<ApiResponse<String>> assignRoomsToExam(Long examId, List<Long> roomIds) {
        Optional<Exam> examOptional = examRepository.findById(examId);

        if (examOptional.isPresent()) {
            Exam exam = examOptional.get();

            // Pour chaque roomId, vérifier si la salle existe et l'ajouter à l'examen
            roomIds.forEach(roomId -> {
                Optional<Room> roomOptional = roomRepository.findById(roomId);
                roomOptional.ifPresent(room -> {
                    exam.getRooms().add(room);
                    room.setIsAvailable(false);
                    roomRepository.save(room); // Marquer la salle comme non disponible
                });
            });

            // Sauvegarder les modifications de l'examen
            examRepository.save(exam);

            return ResponseEntity.ok(ApiResponse.success("Rooms successfully assigned to exam", "Exam ID: " + examId));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Exam not found"));
        }
    }

}
