package com.cnam.demo.controller;


import com.cnam.demo.entity.HistoriqueStock;
import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.entity.Statut;
import com.cnam.demo.entity.Stock;
import com.cnam.demo.repository.HistoriqueStockRepository;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe controller pour la page stock
 */
@Controller
public class StockController {

    @Autowired
    private StockRepository stockrepository;
    @Autowired
    private ProduitRefRepository produitRefRepository;

    @Autowired
    private HistoriqueStockRepository historiqueStockRepository;

    /**
     * Méthode qui capte la méthode get de la page stock et affiche le stock
     * @param model sert à stocker l'entité stock afin d'exploiter l'entité
     * @param keyword sert à capter les entrées de l'utilisateur pour réaliser une recherche par date
     * @return la page stock.html
     */

    @GetMapping("/stock")
    public String getAllStock(Model model, @Param("keyword") Date keyword) {
        try {
            List<Stock> stock = new ArrayList<Stock>();

            if (keyword == null) {
                stockrepository.findAll().forEach(stock::add);
            } else {
                stockrepository.findByDateFabrication(keyword).forEach(stock::add);
                model.addAttribute("keyword", keyword);
            }

            model.addAttribute("stock", stock);

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "stock";
    }

    /**
     * Méthode qui capte la méthode get afin d'afficher le formulaire de création d'un produit dans le stock
     * @param model
     * @return la page stock_form
     */
    @GetMapping("/stock/new")
    public String addProduitStock(Model model) {

        List<ProduitRef> listProduitRef = produitRefRepository.findAll();

        Stock stock = new Stock();

        model.addAttribute("stock", stock);
        model.addAttribute("listProduitRef", listProduitRef);
        model.addAttribute("pageTitle", "Ajout d'un produit en stock");

        return "stock_form";
    }

    /**
     * Méthode qui capte la méthode post pour sauvegarder le modèle en base
     * @param stock modèle contenant les données de l'entité
     * @param redirectAttributes redirige les attributs des modèles
     * @return redirige vers la page stock.html
     */
    @PostMapping("/stock/save")
    public String saveProduitStock(Stock stock,  RedirectAttributes redirectAttributes) {

        try {

            stock.setDateFabrication (new Date());
            Date datePeremption = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(datePeremption);
            c.add(Calendar.HOUR, stock.getProduitRef().getTempsConservation());
            Date currentDatePlusPeremption = c.getTime();
            stock.setDatePeremption(currentDatePlusPeremption);
            stockrepository.save(stock);
            redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/stock";
    }

    /**
     * Méthode qui capte la méthode post pour sauvegarder la modification du statut du modèle en base
     * @param stock modèle contenant les données de l'entité
     * @param redirectAttributes récupère le modèle de la méthode editProduitStock
     * @return la page stock.html
     */
    @PostMapping("/stock/saveEdit")
    public String saveProduitStockEdit( Stock stock, HistoriqueStock historiqueStock, RedirectAttributes redirectAttributes) {

        try {
            historiqueStock.setDateCreation(stock.getDateFabrication());
            historiqueStock.setStock(stock);
            historiqueStockRepository.save(historiqueStock);
            stockrepository.save(stock);

            redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addAttribute("message", e.getMessage());
        }

        return "redirect:/stock";
    }

    /**
     * {id} id c'est un parametre de l'URL, la méthode permet d'éditer le statut
     * @param id
     * @param model
     * @param redirectAttributes
     * @return stock_form_edit.html si le modèle existe sinon redirige vers la page stock.html
     */
    @GetMapping("/stock/{id}")
    public String editProduitStock(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {

            List<Statut> statut = new ArrayList<Statut>();
            List<HistoriqueStock> historiqueStock= new ArrayList<>();

            Stock stock = stockrepository.findById(id).get();
            model.addAttribute("stock", stock);
            model.addAttribute("historiqueStock", historiqueStock);
            model.addAttribute("statut", statut);
            model.addAttribute("pageTitle", "Modifier le statut du produit de référence (ID: " + id + ")");

            return "stock_form_edit";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/stock";
        }
    }

    /**
     * {id} id c'est un parametre de l'URL, la méthode supprime un produit du stock
     * @param id
     * @param redirectAttributes
     * @return supprime le produit et redirige vers stock.html
     */
    @GetMapping("/stock/delete/{id}")
    public String deleteProduitStock(@PathVariable("id") Integer id, Stock stock, ProduitRef produitRef,RedirectAttributes redirectAttributes) {
        try {

            stockrepository.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/stock";
    }
}
