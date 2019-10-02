package com.satransfert.money.modele;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Depot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(75000)
    private Long montant;

    private Date dateDepot;

    @JoinColumn(name = "compte_id",referencedColumnName ="id")
    @ManyToOne(optional = true)
    //@JsonIgnoreProperties("depots")
    @JsonIgnore
    private Compte compte;

    @JoinColumn(name = "caissier_id",referencedColumnName ="id")
    @ManyToOne(//fetch = FetchType.LAZY,
            optional = true)
    @JsonIgnore
    //@JsonIgnoreProperties("depots")
    private User user;


    public Depot() {
    }

    public Depot(Long montant, Date dateDepot, Compte compte, User user) {
        this.montant = montant;
        this.dateDepot = dateDepot;
        this.compte = compte;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontant() {
        return montant;
    }

    public void setMontant(Long montant) {
        this.montant = montant;
    }

    public User getUser() {
        return user;
    }

    public Date getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(Date dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public User getUser(User userConnect) {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

