package com.example.projetemploiexamen.niveau;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface NiveauRepository extends JpaRepository<Niveau, String> {
    Optional<Niveau> findByName(String name);
}
