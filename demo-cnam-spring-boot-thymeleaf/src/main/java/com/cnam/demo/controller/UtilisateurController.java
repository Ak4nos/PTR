package com.cnam.demo.controller;

import com.cnam.demo.entity.Role;
import com.cnam.demo.entity.Utilisateur;
import com.cnam.demo.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/utilisateur")
    public String getAllUtilisateur(Model model, @Param("keyword") String keyword) {
        try {
            List<Utilisateur> utilisateur = new ArrayList<Utilisateur>();

            if (keyword == null) {
                utilisateurRepository.findAll().forEach(utilisateur::add);
            } else {
                utilisateurRepository.findByNom(keyword).forEach(utilisateur::add);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("utilisateur", utilisateur);

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "utilisateur";
    }

    @GetMapping("/utilisateur/new")
    public String addUtilisateur(Model model) {

        Utilisateur utilisateur = new Utilisateur();
        List<Role> role = new ArrayList<Role>();

        model.addAttribute("utilisateur", utilisateur);
        model.addAttribute("role", role);
        model.addAttribute("pageTitle", "Ajout d'un produit en stock");

        return "utilisateur_form";
    }

    @PostMapping("/utilisateur/save")
    public String saveUtilisateur(Utilisateur utilisateur, RedirectAttributes redirectAttributes) {

        try {
            utilisateurRepository.save(utilisateur);

            redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/utilisateur";
    }

    /**
     * {id} id c'est un parametre de l'URL
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/utilisateur/{id}")
    public String editUtilisateur(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<Role> listRole = new ArrayList<Role>();

            Utilisateur utilisateur = utilisateurRepository.findById(id).get();

            model.addAttribute("utilisateur", utilisateur);
            model.addAttribute("listRole", listRole);
            model.addAttribute("pageTitle", "Modifier le produit de référence (ID: " + id + ")");

            return "utilisateur_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/utilisateur";
        }
    }

    @GetMapping("/utilisateur/delete/{id}")
    public String deleteUtilisateur(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            utilisateurRepository.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/utilisateur";
    }

}
