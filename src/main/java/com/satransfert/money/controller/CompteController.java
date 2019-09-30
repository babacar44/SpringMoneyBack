package com.satransfert.money.controller;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/compte")
public class CompteController {

    @Autowired
    CompteRepository compteRepository;


    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Compte ajouterUser(@RequestBody Compte compte){


       return  compteRepository.save(compte);
    }


}
