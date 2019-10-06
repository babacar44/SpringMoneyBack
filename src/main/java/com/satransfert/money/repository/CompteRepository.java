package com.satransfert.money.repository;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte, Long> {
   Optional<Compte> findByNumCompte(String numCompte);

   Optional<Compte> findCompteByNumCompte(String compte);

/*   @Query("SELECT c FROM Compte c WHERE c.numCompte like :x")
   public Compte findCompteByNumCompte(@Param("x") String numCompte);
*/


   @Query("SELECT c FROM Compte c WHERE c.partenaire.id = :x ")
   public List<Compte> listCompte(@Param("x")Long id);
}
