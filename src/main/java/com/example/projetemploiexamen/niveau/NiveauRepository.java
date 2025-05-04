package com.example.projetemploiexamen.niveau;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {

    Optional<Niveau> findById(Long id);

    Optional<Niveau> findByName(String name);

    @Query("SELECT n FROM Niveau n WHERE n.name = :name AND n.td = :td")
    Optional<Niveau> findByNameAndTd(@Param("name") String name, @Param("td") Long td);

    Optional<Niveau> findFirstByName(String niveauName);

}
