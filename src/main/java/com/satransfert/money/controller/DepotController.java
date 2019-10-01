package com.satransfert.money.controller;

import com.satransfert.money.modele.Depot;
import com.satransfert.money.payload.ApiResponse;
import com.satransfert.money.repository.DepotRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;


@RestController
@CrossOrigin
@RequestMapping("/depot")
public class DepotController {

    @Autowired
    DepotRepository depotRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;


    /**
     *
     *
     * @param depot
     * @return depot
     */
    @PreAuthorize("hasAuthority('ROLE_CAISSIER')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> faireDepot(@RequestBody Depot depot){

        Date now=new Date();
        depot.setDateDepot(now);
        depot.setUser(userDetailsService.getUserConnect());

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

        Depot result=  depotRepository.save(depot);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/depot/ajouter")
                .buildAndExpand(result.getCompte() ).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Depot registered successfully"));

    }


}
