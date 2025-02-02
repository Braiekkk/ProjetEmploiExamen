package com.example.projetemploiexamen.utils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class User {
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

}
