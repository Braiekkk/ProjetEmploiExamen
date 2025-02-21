package com.example.projetemploiexamen.Room.DTO;


import com.example.projetemploiexamen.Room.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long roomId; // Identifiant unique de la salle
    private String roomName; // Nom ou numéro de la salle
    private Integer capacity; // Capacité de la salle
    private String location; // Emplacement de la salle
    private Boolean isAvailable; // Statut de disponibilité de la salle

    public RoomDTO(Room room) {
        this.roomId = room.getRoomId();
        this.roomName = room.getRoomName();
        this.capacity = room.getCapacity();
        this.location = room.getLocation();
        this.isAvailable = room.getIsAvailable();
    }
}
