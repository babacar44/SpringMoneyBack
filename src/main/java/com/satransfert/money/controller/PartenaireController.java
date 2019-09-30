package com.satransfert.money.controller;


import com.satransfert.money.modele.*;
import com.satransfert.money.repository.CompteRepository;
import com.satransfert.money.repository.PartenaireRepository;
import com.satransfert.money.repository.RoleRepository;
import com.satransfert.money.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/partenaire")
public class PartenaireController {

    @Autowired
    PartenaireRepository partenaireRepository;
    @Autowired
    CompteRepository compteRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired

    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void ajouterPartenaire(@RequestBody Partenaire partenaire){

        partenaireRepository.save(partenaire);
         User user = new User();
        user.setName(partenaire.getNomComplet());
        user.setAdresse(partenaire.getAdresse());
        user.setEmail(partenaire.getEmail());
        user.setUsername(partenaire.getNomComplet());
        user.setStatut("actif");
        user.setPassword(passwordEncoder.encode("passer"));
        user.setPartenaire(partenaire);
        user.setTelephone(partenaire.getTelephone());

       /* Role role = new Role();
        user.setRoles(role);*/

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ApplicationContextException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);




        Compte compte = new Compte();
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddhhmmss");//210902 251763
        Date now=new Date();
        String numcompte=formater.format(now);
        compte.setNumCompte(numcompte);
        compte.setSolde(0);
        compte.setPartenaire(partenaire);
        compte.setDateCreation(now);
        compteRepository.save(compte);

      //  return  partenaireRepository.save(partenaire);
    }
}
