package com.satransfert.money.modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "partenaire")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 50)
    private String name;

    @NotBlank
    @Size(min=3, max = 50)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(min=6, max = 100)
    private String password;

    @Size(min=6, max = 100)
    private String propriete;

    @NotBlank
    @Size(min=6, max = 15)
    private String telephone;

    @NotBlank
    @Size(min=4, max = 100)
    private String adresse;


    @Size(min=3, max = 100)
    private String statut;

    @Column(nullable = true)
    private String profil;

    private String photo;

    @JoinColumn(name = "partenaire_id",referencedColumnName ="id",nullable = true)
    @ManyToOne(//fetch = FetchType.LAZY,
            optional = true)
    //@JsonIgnoreProperties("users")
    @JsonIgnore
    private Partenaire partenaire;

    @JoinColumn(name = "compte_id",referencedColumnName ="id")
    @ManyToOne(//fetch = FetchType.LAZY,
            optional = true)
    //@JsonIgnoreProperties("users")
   // @JsonIgnore
    private Compte compte;

    @OneToMany(cascade = CascadeType.ALL,
            // = FetchType.LAZY,
            mappedBy ="user")
    @JsonIgnore
   // @JsonIgnoreProperties("user")
    private List<Depot> depots;



    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(@NotBlank @Size(min = 3, max = 50) String name, @NotBlank @Size(min = 3, max = 50) String username, @NotBlank @Size(max = 50) @Email String email, @NotBlank @Size(min = 6, max = 100) String password, @Size(min = 6, max = 100) String propriete, @NotBlank @Size(min = 6, max = 15) String telephone, @NotBlank @Size(min = 4, max = 100) String adresse, @Size(min = 3, max = 100) String statut, String profil, String photo, Partenaire partenaire, Compte compte) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.propriete = propriete;
        this.telephone = telephone;
        this.adresse = adresse;
        this.statut = statut;
        this.profil = profil;
        this.photo = photo;
        this.partenaire = partenaire;
        this.compte = compte;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {return photo; }

    public void setPhoto(String photo) { this.photo = photo; }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Partenaire getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public List<Depot> getDepots() {
        return depots;
    }

    public void setDepots(List<Depot> depots) {
        this.depots = depots;
    }

    public String getPropriete() {
        return propriete;
    }

    public void setPropriete(String propriete) {
        this.propriete = propriete;
    }
}
