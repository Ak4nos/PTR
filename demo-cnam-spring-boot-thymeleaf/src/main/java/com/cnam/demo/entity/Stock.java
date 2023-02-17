package com.cnam.demo.entity;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "date_fabrication", nullable = false)
    @DateTimeFormat//(pattern = "yyyy-MM-dd")
    private Date dateFabrication;

    @Column(name ="date_peremption", nullable = false)
    @DateTimeFormat//(pattern = "yyyy-MM-dd")
    private Date datePeremption;

    @Column (name="statut")
    @Enumerated (EnumType.STRING)
    private Statut statut = Statut.EN_COURS;

    @ManyToOne
    @JoinColumn(name = "produitRef_id")
    private ProduitRef produitRef;


    public Stock() {

    }


    public Stock(Integer id, Date dateFabrication, Date datePeremption, ProduitRef produitRef, Categories categories, Statut statut) {
        this.id = id;
        this.dateFabrication = dateFabrication;
        this.datePeremption = datePeremption;
        this.produitRef = produitRef;
        this.statut = statut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id= id;
    }

    public Date getDateFabrication() {
        return dateFabrication;
    }

    public void setDateFabrication(Date dateFabrication) {
        this.dateFabrication = dateFabrication;
    }

    public Date getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
    }

    public ProduitRef getProduitRef() {
        return produitRef;
    }

    public void setProduitRef(ProduitRef produitRef) {
        this.produitRef = produitRef;
    }



    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", dateFabrication=" + dateFabrication +
                ", datePeremption=" + datePeremption +
                ", statut=" + statut +
                ", produitRef=" + produitRef +
                '}';
    }
}