package com.example.projetemploiexamen.ChefDepartment;

import com.example.projetemploiexamen.utils.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chefdepartment extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long head_id;

    // Constructeur basé sur DTO si nécessaire (comme pour Admin)
    public void ChefDepartement(String email, String password, String name) {
        this.setEmail(email);
        this.setPassword(password);
        this.setName(name);
    }
}
