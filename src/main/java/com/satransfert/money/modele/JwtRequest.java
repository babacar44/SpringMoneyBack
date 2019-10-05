package com.satransfert.money.modele;

import java.io.Serializable;

//Cette classe est requise pour stocker le nom d'utilisateur et le mot de passe que nous avons reçus du client.
public class JwtRequest  {
    private static final long serialVersionUID = 5926468583005150707L;
    private String username;
    private String password;
    //need default constructor for JSON Parsing
    public JwtRequest()
    {
    }
    public JwtRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
