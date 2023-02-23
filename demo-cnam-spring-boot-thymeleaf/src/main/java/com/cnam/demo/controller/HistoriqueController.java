package com.cnam.demo.controller;

import com.cnam.demo.entity.*;
import com.cnam.demo.repository.CategoriesRepository;
import com.cnam.demo.repository.HistoriqueStockRepository;
import com.cnam.demo.repository.ProduitRefRepository;
import com.cnam.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe controller pour l'HistoriqueStock
 */
@Controller
public class HistoriqueController {

    @Autowired
    private StockRepository stockrepository;
    @Autowired
    private HistoriqueStockRepository historiqueStockRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ProduitRefRepository produitRefRepository;

    @GetMapping("/historique")
    public String getHistorique (Model model, @Param("keyword") Date keyword) {
        try {
            List<HistoriqueStock> historiqueStock = new ArrayList<HistoriqueStock>();
            List<ProduitRef> produitRef = new ArrayList<ProduitRef>();
            List<Categories> categories = new ArrayList<Categories>();

            produitRefRepository.findAll().forEach(produitRef::add);
            categoriesRepository.findAll().forEach(categories::add);

            if (keyword == null) {
            historiqueStockRepository.findAll().forEach(historiqueStock::add);
            } else {
                historiqueStockRepository.findByDateCreation(keyword).forEach(historiqueStock::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("produitRef", produitRef);
            model.addAttribute("categories", categories);
            model.addAttribute("historiqueStock", historiqueStock);

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/historique";
    }

}

