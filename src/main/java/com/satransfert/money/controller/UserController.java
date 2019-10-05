package com.satransfert.money.controller;


import com.satransfert.money.modele.User;
import com.satransfert.money.repository.UserRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {


    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping(value = "/modifier/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User modifierUser(@PathVariable Long id, @RequestBody() User u){
        //sachant que l objet c ne contient pas l'id
        //on prend l id du path variable on le stocke dans u objet et on utilise directement c
        u.setId(id);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        return userRepository.save(u);

    }
}
