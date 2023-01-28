package com.cnam.demo.entity;

import javax.persistence.*;
import com.cnam.demo.entity.Categories;

@Entity
@Table(name = "produit_ref")
public class ProduitRef {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 128, nullable = false)
    private String nomProduit;

    @Column(nullable = false)
    private int tempsConservation;

    @Column
    private boolean published;

    @ManyToOne
    @JoinColumn (name = "categories_id")
    private Categories categories;


    public ProduitRef() {
    }

    public ProduitRef(String nomProduit, int tempsConservation, boolean published) {
        this.nomProduit = nomProduit;
        this.tempsConservation = tempsConservation;
        this.published = published;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public int getTempsConservation() {
        return tempsConservation;
    }

    public void setTempsConservation(int tempsConservation) {
        this.tempsConservation = tempsConservation;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "ProduitRef{" +
                "id=" + id +
                ", nomProduit='" + nomProduit + '\'' +
                ", tempsConservation=" + tempsConservation +
                ", published=" + published +
                ", categories=" + categories +
                '}';
    }
}
