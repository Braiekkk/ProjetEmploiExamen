package com.example.projetemploiexamen.admin.DTO;
import com.example.projetemploiexamen.admin.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateAdminDTO {
    private String name;
    private String email;
    private String password;
    private final String role = "ADMIN";

    public CreateAdminDTO(Admin admin) {
        this.name=admin.getName();
        this.email=admin.getEmail();
        this.password=admin.getPassword();

    }

}
