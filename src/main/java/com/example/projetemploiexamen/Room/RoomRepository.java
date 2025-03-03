package com.example.projetemploiexamen.Room;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find a room by its ID
    Optional<Room> findById(Long id);

}
