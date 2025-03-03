package com.example.projetemploiexamen.Room.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoomDTO {
    private String roomName; // Nom ou numéro de la salle
    private Integer capacity; // Capacité de la salle
    private String location; // Emplacement de la salle
}
