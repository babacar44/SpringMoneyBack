package com.satransfert.money.controller;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.payload.ApiResponse;
import com.satransfert.money.repository.CompteRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/compte")

@CrossOrigin

public class CompteController {

    @Autowired
    CompteRepository compteRepository;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

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

    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @GetMapping(value = "/listerComptePartenaire",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<Compte> trouverlescompte(){
        return compteRepository.listCompte(userDetailsService.getUserConnect().getPartenaire().getId());
    }

  /*
    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> affecterCompte(@RequestBody Compte compte) {

    }*/
    }
