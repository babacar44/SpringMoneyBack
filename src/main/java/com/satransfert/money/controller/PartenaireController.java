package com.satransfert.money.controller;


import com.satransfert.money.modele.*;
import com.satransfert.money.payload.ApiResponse;
import com.satransfert.money.repository.CompteRepository;
import com.satransfert.money.repository.PartenaireRepository;
import com.satransfert.money.repository.RoleRepository;
import com.satransfert.money.repository.UserRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @Autowired
    UserDetailsServiceImpl userDetailsService;




    /**
     * ajouter un user
     *
     *
     */

    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @PostMapping(value = "/ajouterUser",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity ajouterUser(@Valid @RequestBody User u){
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        u.setStatut("actif");
      //  u.setPartenaire(userDetailsService.getUserConnect());
        u.setPartenaire(userDetailsService.getUserConnect().getPartenaire());
        if (u.getProfil().equals("1")){
            Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new ApplicationContextException("User Role not set."));

            u.setRoles(Collections.singleton(userRole));

                    }else if(u.getProfil().equals("2")){
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new ApplicationContextException("User Role not set."));

            u.setRoles(Collections.singleton(userRole));
        }else {
            return new ResponseEntity(new ApiResponse(false, "Role doesnt exist !"),
                    HttpStatus.BAD_REQUEST);
        }


        if(userRepository.existsByUsername(u.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByTelephone(u.getTelephone())) {
            return new ResponseEntity(new ApiResponse(false, "Telephone Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(u.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }


        User result = userRepository.save(u);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @PutMapping(value = "/modifierUser/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User modifierUser(@PathVariable Long id, @RequestBody User u){
        //sachant que l objet c ne contient pas l'id
        //on prend l id du path variable on le stocke dans u objet et on utilise directement c
        u.setId(id);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        return  userRepository.save(u);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN_PARTENER')")
    @PutMapping(value = "/bloquerUser/{id}")
    public ResponseEntity<ApiResponse> bloquerUser(@PathVariable Long id ){
        User user = userRepository.findUserById(id);

        if (user.getStatut().equals("actif")){
            user.setStatut("inactif");

            userRepository.save(user);

            return new ResponseEntity(new ApiResponse(true, "statut bloquer now"),
                    HttpStatus.CREATED);
        }
        if (user.getStatut().equals("inactif")){
            user.setStatut("actif");
            userRepository.save(user);

            return new ResponseEntity(new ApiResponse(true, "statut actif now"),
                    HttpStatus.CREATED);
        }
        return null;
    }
}
