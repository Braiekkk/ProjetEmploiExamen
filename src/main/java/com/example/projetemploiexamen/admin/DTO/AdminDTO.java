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
public class AdminDTO {
    private String name;
    private String email;

    public AdminDTO(Admin admin) {
        this.name=admin.getName();
        this.email=admin.getEmail();
    }

}
