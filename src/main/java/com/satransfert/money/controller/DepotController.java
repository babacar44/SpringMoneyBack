package com.satransfert.money.controller;

import com.satransfert.money.modele.*;
import com.satransfert.money.payload.ApiResponse;
import com.satransfert.money.repository.CompteRepository;
import com.satransfert.money.repository.DepotRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/depot")
public class DepotController {

    @Autowired
    DepotRepository depotRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    CompteRepository compteRepository;


    /**
     *
     *
     *
     * @return depot
     */
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> faireDepot(@RequestBody DepotForm depotForm){

        Depot depot = new Depot();
        Date now=new Date();
        depot.setDateDepot(now);
        User user = userDetailsService.getUserConnect();
        depot.setUser(user);
        depot.setMontant(depotForm.getMontant());
        Compte compte = compteRepository.findCompteByNumCompte(depotForm.getNumCompte()).orElseThrow(() -> new ApplicationContextException("Compte  not found."));

        if(depot.getMontant()<= 75000) {
            return new ResponseEntity(new ApiResponse(false, "Solde de depot doit etre supérieur à 75000 F CFA!"),
                    HttpStatus.BAD_REQUEST);
        }


        if(depot.getCompte()==null) {
            return new ResponseEntity(new ApiResponse(false, "Compte not found"),
                    HttpStatus.BAD_REQUEST);
        }
        if(depot.getUser()==null) {
            return new ResponseEntity(new ApiResponse(false, "UserConnect not found"),
                    HttpStatus.BAD_REQUEST);
        }
        depot.setCompte(compte);

        compte.setSolde((int) (compte.getSolde()+depotForm.getMontant()));
        compteRepository.save(compte);
        Depot result=  depotRepository.save(depot);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/depot/ajouter")
                .buildAndExpand(result.getCompte() ).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Depot registered successfully"));

    }


}
