package com.cnam.demo.entity;

import javax.persistence.*;
import com.cnam.demo.entity.Categories;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne (
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinColumn (name = "categories_id")
    private Categories categories;

    @OneToMany(
            mappedBy = "produitRef",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Stock> stockList = new ArrayList<>();

    public ProduitRef() {
    }

    public ProduitRef(String nomProduit, int tempsConservation) {
        this.nomProduit = nomProduit;
        this.tempsConservation = tempsConservation;
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

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public int getValueOfCategories(){

        return categories.getId();
    }

    @Override
    public String toString() {
        return "ProduitRef{" +
                "id=" + id +
                ", nomProduit='" + nomProduit + '\'' +
                ", tempsConservation=" + tempsConservation +
                ", categories=" + categories +
                '}';
    }
}
