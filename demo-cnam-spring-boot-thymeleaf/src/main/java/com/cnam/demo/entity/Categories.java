package com.cnam.demo.entity;

import javax.persistence.*;

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

  @Column
  private boolean published;

  public Categories() {

  }

  public Categories(String designation, String description, boolean published) {
    this.designation = designation;
    this.description = description;
    this.published = published;
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

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean published) {
    this.published = published;
  }

  @Override
  public String toString() {
    return "Categories [id=" + id + ", designation=" + designation + ", description=" + description + ", published=" + published + "]";
  }

}
