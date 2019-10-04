package com.satransfert.money.repository;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
       @Query("SELECT p FROM Partenaire p WHERE p.id = :x")
     Partenaire findPartenaireById(@Param("x") Long id);



}
