package com.example.projetemploiexamen.auth.DTO;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    private String email;
    private String password;
    private String role;
}