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
public class UpdateDepartmentDTO {
    private String name;
    //the updated_at attribute is automatically managed by spring hibernate
}
