package com.example.projetemploiexamen.Teacher;

import com.example.projetemploiexamen.Teacher.DTO.CreateTeacherDTO;
import com.example.projetemploiexamen.department.Department;
import com.example.projetemploiexamen.utils.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Teacher extends User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long departmentId;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;


    public Teacher(CreateTeacherDTO dto,Department department) {
        this.setEmail(dto.getEmail());
        this.setPassword(dto.getPassword());
        this.setName(dto.getName());
        this.department = department;
    }

}
