package com.cnam.demo.controller;

import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.entity.Statut;
import com.cnam.demo.entity.Stock;
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


@Controller
public class historiqueController {

    @Autowired
    private StockRepository stockrepository;
    @Autowired
    private ProduitRefRepository produitRefRepository;

    @GetMapping("/historique")
    public String getHistorique (Model model, @Param("keyword") Date keyword) {
        try {
            List<Stock> stock = new ArrayList<Stock>();

            List<ProduitRef> produitRef = new ArrayList<ProduitRef>();
            List<Statut> statut = new ArrayList<>();
            if (keyword == null) {
            stockrepository.findByStatut(Statut.JETE).forEach(stock::add);
            stockrepository.findByStatut(Statut.CONSOMME).forEach(stock::add);
            } else {
                stockrepository.findByDateFabrication(keyword).forEach(stock::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("stock", stock);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/historique";
    }

}

