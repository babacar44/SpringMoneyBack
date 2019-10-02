package com.satransfert.money.repository;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Depot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepotRepository extends JpaRepository<Depot, Long> {
  // Optional<Compte> findByNumCompte(String compte);



}
