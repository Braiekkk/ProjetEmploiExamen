package com.example.projetemploiexamen.auth.DTO;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String role;
}