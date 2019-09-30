package com.satransfert.money.repository;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
   //Optional<Compte> findByNumcompte(String compte);

}
