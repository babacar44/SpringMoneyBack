package com.satransfert.money.repository;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
   //Optional<Compte> findByNumcompte(String compte);

}
