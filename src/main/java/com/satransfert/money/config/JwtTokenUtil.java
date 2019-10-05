package com.satransfert.money.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.satransfert.money.modele.Role;
import com.satransfert.money.modele.RoleName;
import com.satransfert.money.modele.User;
import com.satransfert.money.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.CrossOrigin;

//Le  JwtTokenUtil est responsable de l'exécution d'opérations JWT telles que la création et la validation
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    //encodage token
    @Value("${jwt.secret}")
    private String secret;
    //récupère le nom d'utilisateur du jeton jwt
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //récupère la date d'expiration du jeton jwt
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //pour récupérer toute information d'un jeton, nous aurons besoin de la clé secrète
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    //vérifie si le jeton a expiré
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //générer un jeton pour l'utilisateur
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
    @Autowired
    UserRepository userRepository;
    // lors de la création du jeton -
//1. Définir les revendications du jeton, telles que l'émetteur, l'expiration, le sujet et l'ID
// 2. Signez le JWT en utilisant l'algorithme HS512 et la clé secrète.
// 3. Selon la sérialisation JWS compacte (https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
// compactage du JWT en une chaîne sécurisée par les URL
    private String doGenerateToken(Map<String, Object> claims, String subject) {

           User user= userRepository.findByUsername(subject).orElseThrow();

        String auth = null;
        for (Role role: user.getRoles()
        ) {
            RoleName rien=role.getName();
            System.out.println(rien);
            auth=rien.name();
            //System.out.println(auth);

        }
          // return user;
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).claim("role",auth)
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

