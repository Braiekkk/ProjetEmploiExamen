package com.example.projetemploiexamen.department.DTO;


import com.example.projetemploiexamen.department.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private String name;        // Nom du département
    //private String head;        // Nom du chef du département (ou ID si nécessaire)

    public DepartmentDTO(Department departement) {
        this.name = departement.getName();
       // ToDo : Chef department
    }
}
