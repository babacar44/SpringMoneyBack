package com.satransfert.money.controller;




import com.satransfert.money.config.JwtTokenUtil;
import com.satransfert.money.modele.*;
import com.satransfert.money.repository.UserRepository;
import com.satransfert.money.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController //ou Controller
@CrossOrigin
//Cors fais la liaison entre les apps
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager; //À l'aide de Spring Authentication Manager, nous authentifions le nom d'utilisateur et le mot de passe
   //Si les informations d'identification sont valides, un jeton JWT est créé à l'aide de  JWTTokenUtil et est fourni au client.
    @Autowired
    //
    private JwtTokenUtil jwtTokenUtil; //creation et validation du token
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    UserRepository userRepository;




    @RequestMapping(value = "/authenticate", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        //genere token
        final String token = jwtTokenUtil.generateToken(userDetails);
        userDetails.getAuthorities().forEach(u ->{
            System.out.println(u.getAuthority());
        });
        //retourne token en json
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST,consumes = {MediaType.APPLICATION_JSON_VALUE})
    //, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE }
    public @ResponseBody String createLoginToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new ApplicationContextException("Not Found"));
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if (user.getStatut().equalsIgnoreCase("inactif") || (user.getPartenaire().getStatut().equalsIgnoreCase("inactif"))){
            return  "Vous etes bloqué ";
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
       userDetails.getAuthorities().forEach(u ->{
            System.out.println(u.getAuthority());
        });
        return token;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
