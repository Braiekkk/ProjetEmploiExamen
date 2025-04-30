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
public class CreateRoomDTO {
    private String name; // Nom ou numéro de la salle
    private Integer capacity; // Capacité de la salle
    private String location; // Emplacement de la salle

    public CreateRoomDTO(Room room) {
        this.name = room.getName();
        this.capacity = room.getCapacity();
        this.location = room.getLocation();
    }
}
