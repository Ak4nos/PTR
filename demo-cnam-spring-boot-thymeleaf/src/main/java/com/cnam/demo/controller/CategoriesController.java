package com.cnam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import com.cnam.demo.entity.Categories;
import com.cnam.demo.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoriesController {

  @Autowired
  private CategoriesRepository categoriesRepository;

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

  @GetMapping("/categories/new")
  public String addCategories(Model model) {
    Categories categories = new Categories();
    categories.setPublished(true);

    model.addAttribute("categories", categories);
    model.addAttribute("pageTitle", "Création d'une catégorie");

    return "categories_form";
  }

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
   * {id} id c'est un parametre de l'URL
   * @param id
   * @param model
   * @param redirectAttributes
   * @return
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
  public String deleteCategories(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      categoriesRepository.deleteById(id);
     /// tutorialRepository.findByDescriptionProduct("kkk");

      redirectAttributes.addFlashAttribute("message", "The Categories with id=" + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/categories";
  }

  @GetMapping("/categories/{id}/published/{status}")
  public String updateCategoriesPublishedStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean published,
      Model model, RedirectAttributes redirectAttributes) {
    try {
      categoriesRepository.updatePublishedStatus(id, published);

      String status = published ? "published" : "disabled";
      String message = "The Categories id=" + id + " has been " + status;

      redirectAttributes.addFlashAttribute("message", message);
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/categories";
  }
}
