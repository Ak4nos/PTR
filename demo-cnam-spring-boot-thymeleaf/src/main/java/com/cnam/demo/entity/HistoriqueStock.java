package com.cnam.demo.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "historique_stock")
public class HistoriqueStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column (name="date_creation", nullable = false)
    @DateTimeFormat
    private Date dateCreation;

    @ManyToOne
            (
                    cascade = {
                            CascadeType.PERSIST,
                            CascadeType.MERGE
                    }
            )
    @JoinColumn(name="stock_id")
    private Stock stock;

    public HistoriqueStock() {
    }

    public HistoriqueStock(Integer id, Stock stock) {
        this.id = id;
        this.stock = stock;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Integer getStockId (){
        return stock.getId();
    }

    @Override
    public String toString() {
        return "HistoriqueStock{" +
                "id=" + id +
                ", stock=" + stock +
                '}';
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}
