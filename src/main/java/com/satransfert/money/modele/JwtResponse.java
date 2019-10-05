package com.satransfert.money.modele;

import java.io.Serializable;

//Cette classe est requise pour créer une réponse contenant le JWT à renvoyer à l'utilisateur.
public class JwtResponse  {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    public JwtResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }
    public String getToken() {
        return this.jwttoken;
    }
}
