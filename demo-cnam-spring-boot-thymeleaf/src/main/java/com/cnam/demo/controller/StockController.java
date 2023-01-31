package com.cnam.demo.controller;


import com.cnam.demo.entity.Categories;
import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.entity.Statut;
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

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockRepository stockrepository;
    @Autowired
    private ProduitRefRepository produitRefRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;


    @GetMapping("/stock")
    public String getAllStock(Model model, @Param("keyword") String keyword) {
        try {
            List<Stock> stock = new ArrayList<Stock>();

            List<ProduitRef> produitRef = new ArrayList<ProduitRef>();
            List<Categories> categories = new ArrayList<Categories>();
            if (keyword == null) {
                stockrepository.findAll().forEach(stock::add);
            } else {
                //produitRefRepository.findByNomProduitContainingIgnoreCase(keyword).forEach(stock::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("stock", stock);

            model.addAttribute("produitRef", produitRef);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "stock";
    }

    @GetMapping("/stock/new")
    public String addProduitStock(Model model) {

        List<ProduitRef> listProduitRef = produitRefRepository.findAll();
        List<Categories> listCategories = categoriesRepository.findAll();

        Stock stock = new Stock();

        model.addAttribute("stock", stock);
        model.addAttribute("listProduitRef", listProduitRef);
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Ajout d'un produit en stock");

        return "stock_form";
    }

    @PostMapping("/stock/save")
    public String saveProduitStock(Stock stock,  RedirectAttributes redirectAttributes) {
        //List<ProduitRef> listProduitRef = produitRefRepository.findAll();

        try {

            stock.setDateFabrication(new Date());
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
     * {id} id c'est un parametre de l'URL
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/stock/{id}")
    public String editProduitStock(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            List<ProduitRef> listProduitRef = produitRefRepository.findAll();
            List<Categories> listCategories = categoriesRepository.findAll();
            List<Statut> statut = new ArrayList<Statut>();


            Stock stock = stockrepository.findById(id).get();

            model.addAttribute("stock", stock);
            model.addAttribute("statut", statut);
            model.addAttribute("listProduitRef", listProduitRef);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Modifier le statut du produit de référence (ID: " + id + ")");

            return "stock_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/stock";
        }
    }

    @GetMapping("/stock/delete/{id}")
    public String deleteProduitStock(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            stockrepository.deleteById(id);

            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/stock";
    }


}
