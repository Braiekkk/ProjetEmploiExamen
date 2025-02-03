package com.example.projetemploiexamen.department.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateDepartmentDTO {
    private String name;
    private Timestamp created_at;
    private Timestamp updated_at;
}
