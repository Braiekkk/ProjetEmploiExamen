package com.example.projetemploiexamen.Room;

import com.example.projetemploiexamen.Room.DTO.CreateRoomDTO;
import com.example.projetemploiexamen.Room.DTO.RoomDTO;
import com.example.projetemploiexamen.Room.DTO.UpdateRoomDTO;
import com.example.projetemploiexamen.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RoomDTO>> createRoom(@RequestBody CreateRoomDTO room) {
        return roomService.createRoom(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomDTO>> updateRoom(@PathVariable Long id, @RequestBody UpdateRoomDTO roomDetails) {
        return roomService.updateRoom(id, roomDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteRoom(@PathVariable Long id) {
        return roomService.deleteRoom(id);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Room>>> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoomDTO>> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id);
    }

    
}
