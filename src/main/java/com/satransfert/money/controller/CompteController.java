package com.satransfert.money.controller;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.payload.ApiResponse;
import com.satransfert.money.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;


@RestController
@RequestMapping("/compte")

@CrossOrigin(origins = "http://localhost:4200")

public class CompteController {

    @Autowired
    CompteRepository compteRepository;


    /**
     *
     *
     * @param compte
     * @return compte
     */
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> ajouterCompte(@RequestBody Compte compte){

        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddhhmmss");//210902 251763
        Date now=new Date();
        String numcompte=formater.format(now);
        compte.setNumCompte(numcompte);
        compte.setDateCreation(now);

        Compte result=  compteRepository.save(compte);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/compte/ajouter")
                .buildAndExpand(result.getDepots() ).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Compte registered successfully"));

    }


}
