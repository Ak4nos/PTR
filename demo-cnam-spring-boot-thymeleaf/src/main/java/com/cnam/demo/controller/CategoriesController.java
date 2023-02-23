package com.cnam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.cnam.demo.entity.Categories;
import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.entity.Stock;
import com.cnam.demo.repository.CategoriesRepository;
import com.cnam.demo.repository.ProduitRefRepository;
import com.cnam.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Classe controller pour les catégories
 */
@Controller
public class CategoriesController {

  @Autowired
  private CategoriesRepository categoriesRepository;
  @Autowired
  private ProduitRefRepository produitRefRepository;

  /**
   * Méthode qui capte la méthode get de la page catégories et affiche les catégories
   * @param model sert à stocker l'entité Categories afin d'exploiter l'entité
   * @param keyword sert à capter les entrées de l'utilisateur pour réaliser une recherche par désignation
   * @return
   */
  @GetMapping("/categories")
  public String getAll(Model model, @Param("keyword") String keyword) {
    try {
      List<Categories> categories = new ArrayList<Categories>();

      if (keyword == null) {
        categoriesRepository.findAll().forEach(categories::add);
      } else {
        categoriesRepository.findByDesignationContainingIgnoreCase(keyword).forEach(categories::add);
        model.addAttribute("keyword", keyword);
      }

      model.addAttribute("categories", categories);
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "categories";
  }

  /**
   * Méthode qui capte la méthode get afin d'afficher le formulaire de création de catégories
   * @param model
   * @return la page categories_form.html
   */
  @GetMapping("/categories/new")
  public String addCategories(Model model) {
    Categories categories = new Categories();

    model.addAttribute("categories", categories);
    model.addAttribute("pageTitle", "Création d'une catégorie");

    return "categories_form";
  }
  /**
   * Méthode qui capte la méthode post pour sauvegarder le modèle en base
   * @param categories modèle contenant les données de l'entité
   * @param redirectAttributes récupère le modèle de la méthode addCategories
   * @return la page categories.html
   */
  @PostMapping("/categories/save")
  public String saveCategories(Categories categories, RedirectAttributes redirectAttributes) {
    try {
      categoriesRepository.save(categories);

      redirectAttributes.addFlashAttribute("message", "The Categories has been saved successfully!");
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/categories";
  }

  /**
   * {id} id c'est un parametre de l'URL, la méthode permet d'éditer la categorie
   * @param id
   * @param model
   * @param redirectAttributes
   * @return redirige vers la page categories.html
   */
  @GetMapping("/categories/{id}")
  public String editCategories(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Categories categories = categoriesRepository.findById(id).get();

      model.addAttribute("categories", categories);
      model.addAttribute("pageTitle", "Modifier la catégorie (ID: " + id + ")");

      return "categories_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/categories";
    }
  }

  @GetMapping("/categories/delete/{id}")
  public String deleteCategories(@PathVariable("id") Integer id, Model model , Categories categories, RedirectAttributes redirectAttributes) {
    try {

        categoriesRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("message", "The Categories with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }
    return "redirect:/categories";
  }
}
