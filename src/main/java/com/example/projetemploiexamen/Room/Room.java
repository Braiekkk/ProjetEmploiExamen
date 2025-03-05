package com.example.projetemploiexamen.Room;

import com.example.projetemploiexamen.Room.DTO.CreateRoomDTO;
import com.example.projetemploiexamen.Room.DTO.UpdateRoomDTO;
import com.example.projetemploiexamen.exam.Exam;
import com.example.projetemploiexamen.niveau.Niveau;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique de la salle

    @Column(nullable = false, length = 50)
    private String name; // Nom ou numéro de la salle

    @Column(nullable = false)
    private Integer capacity; // Capacité de la salle

    @Column(nullable = false, length = 100)
    private String location; // Emplacement de la salle (bâtiment, étage)

    @OneToMany(mappedBy = "room", cascade = CascadeType.PERSIST)
    private List<Exam> exams ;

    public Room (CreateRoomDTO dto) {
        this.capacity = dto.getCapacity();
        this.name = dto.getName();
        this.location = dto.getLocation();
    }


}
