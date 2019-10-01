package com.satransfert.money.controller;


import com.satransfert.money.modele.Role;
import com.satransfert.money.modele.RoleName;
import com.satransfert.money.modele.User;
import com.satransfert.money.payload.ApiResponse;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserRepository userRepository;

    @Autowired

    PasswordEncoder passwordEncoder;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RoleRepository roleRepository;



    /**
     * lister les users
     */
    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping(value = "/lister",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> liste(){
            return userRepository.findAll();
    }

    /**
     * consulter un user
     */

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @GetMapping(value = "/lister/{id}")
    public Optional<User> listerOneUser(@PathVariable Long id){
        return  userRepository.findById(id);
    }


    /**
     * ajouter un user
     * @return user
     * @return new message
     */

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PostMapping(value = "/ajouter",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity ajouterUser(@Valid @RequestBody User u){
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        if (u.getProfil().equals("1")){
            Role userRole = roleRepository.findByName(RoleName.ROLE_CAISSIER)
                    .orElseThrow(() -> new ApplicationContextException("User Role not set."));

            u.setRoles(Collections.singleton(userRole));
        }else if(u.getProfil().equals("2")){
            Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN_WARI)
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
        //User result = userRepository.save(u);

      //  return ResponseEntity.created().body("User registered successfully"));
        User result = userRepository.save(u);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    /**
     * ajouter un user
     * @return user/id
     */

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @PutMapping(value = "/modifier/{id}",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User modifierUser(@PathVariable Long id, @RequestBody User u){
        //sachant que l objet c ne contient pas l'id
        //on prend l id du path variable on le stocke dans u objet et on utilise directement c
        u.setId(id);
        u.setPassword(passwordEncoder.encode(u.getPassword()));

        return  userRepository.save(u);
    }



}
