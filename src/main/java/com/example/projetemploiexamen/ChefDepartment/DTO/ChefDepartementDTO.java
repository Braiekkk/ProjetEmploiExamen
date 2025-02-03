package com.example.projetemploiexamen.ChefDepartment.DTO;

import com.example.projetemploiexamen.ChefDepartment.Chefdepartment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChefDepartementDTO {
    private String name;
    private String email;

    public ChefDepartementDTO(Chefdepartment chefDepartement) {
        this.name = chefDepartement.getName();
        this.email = chefDepartement.getEmail();
    }
}