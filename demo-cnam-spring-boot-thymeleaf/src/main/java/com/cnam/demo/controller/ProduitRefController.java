package com.cnam.demo.controller;


import com.cnam.demo.entity.Categories;
import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.repository.CategoriesRepository;
import com.cnam.demo.repository.ProduitRefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe controller pour la page produitRef
 */
@Controller
public class ProduitRefController {

    @Autowired
    private ProduitRefRepository produitRefRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;

    /**
     * Méthode qui capte le get pour afficher tous les produits de références
     * @param model sert à stocker l'entité produitRef afin d'exploiter l'entité
     * @param keyword sert à capter les entrées de l'utilisateur pour réaliser une recherche
     * @return produitRef.html
     */

    @GetMapping("/produitRef")
    public String getAllProduitRef(Model model, @Param("keyword") String keyword) {
        try {
            List<ProduitRef> produitRef = new ArrayList<ProduitRef>();
            List<Categories> categories = new ArrayList<Categories>();

            if (keyword == null) {
                produitRefRepository.findAll().forEach(produitRef::add);
            } else {
                produitRefRepository.findByNomProduitContainingIgnoreCase(keyword).forEach(produitRef::add);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("produitRef", produitRef);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "produitRef";
    }

    /**
     * Méthode qui capte le get pour afficher la page formulaire de création d'un produit de référence
     * @param model sert à stocker l'entité afin de l'exploiter
     * @return produitRef_form
     */
    @GetMapping("/produitRef/new")
    public String addProduitRef(Model model) {
        List<Categories> listCategories = categoriesRepository.findAll();

        ProduitRef produitRef = new ProduitRef();

        model.addAttribute("produitRef", produitRef);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Création de produit de référence");

        return "produitRef_form";
    }

    /**
     * Méthode qui capte la méthode post pour sauvegarder le modèle en base
     * @param produitRef modèle contenant les données de l'entité
     * @param redirectAttributes redirige les attributs des modèles
     * @return redirige vers la page produitRef.html
     */
    @PostMapping("/produitRef/save")
    public String saveProduitRef(ProduitRef produitRef, RedirectAttributes redirectAttributes) {

        try {
            produitRefRepository.save(produitRef);

            redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/produitRef";
    }

    /**
     * {id} id c'est un parametre de l'URL, capte le get pour éditer le statut
     * @param id
     * @param model
     * @param redirectAttributes
     * @return produitref_form.html si le produit existe, redirige vers produitref.html sinon avec un message
     */
    @GetMapping("/produitRef/{id}")
    public String editProduitRef(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Categories> listCategories = categoriesRepository.findAll();

            ProduitRef produitRef = produitRefRepository.findById(id).get();

            model.addAttribute("produitRef", produitRef);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Modifier le produit de référence (ID: " + id + ")");

            return "produitRef_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/produitRef";
        }
    }

    /**
     * {id} id c'est un parametre de l'URL, la méthode supprime un produit du stock
     * @param id
     * @param redirectAttributes
     * @return vers la page produitRef.html
     */
    @GetMapping("/produitRef/delete/{id}")
    public String deleteProduitRef(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {

                produitRefRepository.deleteById(id);

                redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/produitRef";
    }
}
