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
public class UpdateChefDepartementDTO {
    private String name;
    private String email;

    public UpdateChefDepartementDTO(Chefdepartment chefDepartement) {
        this.name = chefDepartement.getName();
        this.email = chefDepartement.getEmail();
    }
}