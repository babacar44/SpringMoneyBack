package com.satransfert.money.controller;

import com.satransfert.money.modele.Compte;
import com.satransfert.money.modele.Partenaire;
import com.satransfert.money.payload.ApiResponse;
import com.satransfert.money.repository.CompteRepository;
import com.satransfert.money.repository.PartenaireRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContextException;
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
    @Autowired
    PartenaireRepository partenaireRepository;


    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> ajouterCompte(@RequestBody Partenaire p){
        Compte compte = new Compte();

        Partenaire partenaire = partenaireRepository.findById(p.getId()).orElseThrow(() -> new ApplicationContextException("Partenaire  not found."));
        compte.setPartenaire(partenaire);
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddhhmmss");//210902 251763
        Date now=new Date();
        String numcompte=formater.format(now);
        compte.setNumCompte(numcompte);
        compte.setDateCreation(now);
        compte.setSolde(0);

        Compte result=  compteRepository.save(compte);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/lister")
                .buildAndExpand(result.getNumCompte() ).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Compte registered successfully"));

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @GetMapping(value = "/lister")
    public List<Compte> trouverlescompte(){
        return compteRepository.listCompte(userDetailsService.getUserConnect().getPartenaire().getId());
    }


    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping(value = "/listerAllCompte")
    public List<Compte> trouverTouslescompte(){
        return compteRepository.findAll();
    }
  /*
    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ApiResponse> affecterCompte(@RequestBody Compte compte) {
    }*/
}
