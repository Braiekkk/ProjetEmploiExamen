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
public class CreateChefDepartementDTO {
    private String name;
    private String email;
    private String password;
    private final String role = "CHEF_DEPARTEMENT";  // Spécifie le rôle de Chef de Département

    public CreateChefDepartementDTO(Chefdepartment chefDepartement) {
        this.name = chefDepartement.getName();
        this.email = chefDepartement.getEmail();
        this.password = chefDepartement.getPassword();
    }
}
