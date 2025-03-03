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



}
