package com.satransfert.money.modele;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "compte", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "numCompte"
        })
})
public class Compte implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String numCompte;

    private Integer solde = 0;

    @JoinColumn(name = "partenaire_id",referencedColumnName ="id")
    @ManyToOne(optional = false)
    @JsonIgnoreProperties("comptes")
    private Partenaire partenaire;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy ="compte")
    @JsonIgnoreProperties("compte")//mappage avec l objet service
    private List<User> users;

    private Date dateCreation;

   // private Collection<Operation> operations;

    public Compte() {
    }


    public Compte(@NotBlank @Size(min = 3, max = 50) String numCompte, Integer solde, Partenaire partenaire, Date dateCreation) {
        this.numCompte = numCompte;
        this.solde = solde;
        this.partenaire = partenaire;
        this.dateCreation = dateCreation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumCompte() {
        return numCompte;
    }

    public void setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }

    public Integer getSolde() {
        return solde;
    }

    public void setSolde(Integer solde) {
        this.solde = solde;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
