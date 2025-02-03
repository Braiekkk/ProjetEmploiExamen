package com.example.projetemploiexamen.admin;

import com.example.projetemploiexamen.admin.DTO.CreateAdminDTO;

import com.example.projetemploiexamen.utils.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Admin(CreateAdminDTO dto) {
        this.setEmail(dto.getEmail());
        this.setPassword(dto.getPassword());
        this.setName(dto.getName());
    }
}
