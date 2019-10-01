package com.satransfert.money.repository;

import com.satransfert.money.modele.Depot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepotRepository extends JpaRepository<Depot, Long> {
   //Optional<Compte> findByNumcompte(String compte);

}
