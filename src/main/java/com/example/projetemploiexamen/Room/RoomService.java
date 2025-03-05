package com.example.projetemploiexamen.Room;

import com.example.projetemploiexamen.Room.DTO.CreateRoomDTO;
import com.example.projetemploiexamen.Room.DTO.RoomDTO;
import com.example.projetemploiexamen.Room.DTO.UpdateRoomDTO;
import com.example.projetemploiexamen.exam.ExamRepository;
import com.example.projetemploiexamen.student.Student;
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
    public ResponseEntity<ApiResponse<RoomDTO>> createRoom(CreateRoomDTO room) {
        try {
            Room r = new Room (room);
            roomRepository.save(r);
            return ResponseEntity.ok(ApiResponse.success("Room created successfully", new RoomDTO(r)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create room"));
        }
    }

    // Update room details
    public ResponseEntity<ApiResponse<RoomDTO>> updateRoom(Long id, UpdateRoomDTO roomDetails) {
        Optional<Room> existingRoom = roomRepository.findById(id);
        if (existingRoom.isPresent()) {
            Room room = existingRoom.get();
            room.setName(roomDetails.getName());
            room.setCapacity(roomDetails.getCapacity());
            room.setLocation(roomDetails.getLocation());
            roomRepository.save(room);
            return ResponseEntity.ok(ApiResponse.success("Room updated successfully", new RoomDTO(room)));
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
    public ResponseEntity<ApiResponse<RoomDTO>> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(room -> ResponseEntity.ok(ApiResponse.success("Room retrieved successfully", new RoomDTO(room))))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Room not found")));
    }

}




