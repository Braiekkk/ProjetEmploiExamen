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
    private Long id; // Identifiant unique de la salle
    private String name; // Nom ou numéro de la salle
    private Integer capacity; // Capacité de la salle
    private String location; // Emplacement de la salle

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.capacity = room.getCapacity();
        this.location = room.getLocation();
    }
}
