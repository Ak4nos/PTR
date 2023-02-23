package com.cnam.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categories {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(length = 128, nullable = false)
  private String designation;

  @Column(length = 256)
  private String description;

  @OneToMany(
          mappedBy = "categories",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
    List<ProduitRef> produitRefList = new ArrayList<>();

  public Categories() {

  }

  public Categories(String designation, String description) {
    this.designation = designation;
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }



  @Override
  public String toString() {
    return "Categories [id=" + id + ", designation=" + designation + ", description=" + description + "]";
  }

}
