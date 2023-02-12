package com.cnam.demo.entity;


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
    private Date dateFabrication;

    @Column(name ="date_peremption", nullable = false)
    private Date datePeremption;

    @Column (name="statut")
    @Enumerated (EnumType.STRING)
    private Statut statut = Statut.EN_COURS;

    @ManyToOne
    @JoinColumn(name = "produitRef_id")
    private ProduitRef produitRef;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;


    public Stock() {

    }


    public Stock(Integer id, Date dateFabrication, Date datePeremption, ProduitRef produitRef, Categories categories, Statut statut) {
        this.id = id;
        this.dateFabrication = dateFabrication;
        this.datePeremption = datePeremption;
        this.produitRef = produitRef;
        this.categories = categories;
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

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
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
                ", categories=" + categories +
                '}';
    }
}