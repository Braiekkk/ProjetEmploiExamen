package com.example.projetemploiexamen.department;

import com.example.projetemploiexamen.Teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long department_id;

    @Column(nullable = false, length = 100)
    private String name;

    // Timestamp géré automatiquement lors de la création
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp created_at;

    // Timestamp géré automatiquement lors de la mise à jour
    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updated_at;

    // Relation OneToMany avec Teacher
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Teacher> teachers;
}
