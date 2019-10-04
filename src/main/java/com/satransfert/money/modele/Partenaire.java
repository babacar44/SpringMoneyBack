package com.satransfert.money.modele;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "partenaire", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "ninea"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
        @UniqueConstraint(columnNames = {
                "telephone"
        }),
        @UniqueConstraint(columnNames = {
                "raisonSociale"
        })
})
public class Partenaire implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String ninea;

    @NotBlank
    @Size(min=3, max = 50)
    private String raisonSociale;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NotBlank
    @Size(min=3, max = 50)
    private String telephone;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=3, max = 50)
    private String adresse;

    @NotBlank
    @Size(min=3, max = 50)
    private String statut;

    /*@OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();*/

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy ="partenaire")
    @JsonIgnoreProperties("partenaire")
    private List<User> users;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy ="partenaire")
    @JsonIgnoreProperties("partenaire")//mappage avec l objet service
    private List<Compte> comptes;


    public Partenaire() {
    }

    public Partenaire(@NotBlank @Size(min = 3, max = 50) String ninea, @NotBlank @Size(min = 3, max = 50) String raisonSociale, @NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(min = 3, max = 50) String telephone, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 3, max = 50) String adresse, @NotBlank @Size(min = 3, max = 50) String statut, List<User> users, List<Compte> comptes) {
        this.ninea = ninea;
        this.raisonSociale = raisonSociale;
        this.username = username;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.statut = statut;
        this.users = users;
        this.comptes = comptes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNinea() {
        return ninea;
    }

    public void setNinea(String ninea) {
        this.ninea = ninea;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public List<User> getUserList() {
        return users;
    }

    public void setUserList(List<User> userList) {
        this.users = userList;
    }

    public List<Compte> getComptes() {
        return comptes;
    }

    public void setComptes(List<Compte> comptes) {
        this.comptes = comptes;
    }
}
