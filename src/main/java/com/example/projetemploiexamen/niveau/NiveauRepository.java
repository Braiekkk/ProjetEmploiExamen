package com.example.projetemploiexamen.niveau;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {

    Optional<Niveau> findById(Long id);

    Optional<Niveau> findByName(String name);

    Optional<Niveau> findByNameAndTd(String name, Long td);
}
