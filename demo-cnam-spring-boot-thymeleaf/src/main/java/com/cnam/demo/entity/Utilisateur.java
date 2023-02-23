package com.cnam.demo.entity;

import javax.persistence.*;

/**
 * Classe de création de l'entité utilisateur
 */
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id", nullable = false)
    private Integer id;

    @Column (name = "nom", nullable = false)
    private String nom;

    @Column (name = "prenom", nullable = false)
    private String prenom;

    @Column (name = "login", nullable = false)
    private String login;

    @Column (name = "adresse_mail", nullable = false)
    private String adresseMail;

    @Column (name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column (name = "role", nullable = false)
    @Enumerated (EnumType.STRING)
    private Role role;

    public Utilisateur() {
    }

    public Utilisateur(Integer id, String nom, String prenom, String login, String adresseMail, String motDePasse, Role role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.login = login;
        this.adresseMail = adresseMail;
        this.motDePasse = motDePasse;
        this.role = role;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", login='" + login + '\'' +
                ", adresseMail='" + adresseMail + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
