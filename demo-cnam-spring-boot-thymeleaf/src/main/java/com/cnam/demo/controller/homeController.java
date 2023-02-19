package com.cnam.demo.controller;

import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.entity.Stock;
import com.cnam.demo.repository.ProduitRefRepository;
import com.cnam.demo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class homeController {

    @Autowired
    private StockRepository stockrepository;
    @Autowired
    private ProduitRefRepository produitRefRepository;

    @GetMapping("/home")
    public String getAllAccueil (Model model, @Param("keyword") String keyword) {
        try {
            List<Stock> stock = new ArrayList<Stock>();

            List<ProduitRef> produitRef = new ArrayList<ProduitRef>();
            if (keyword == null) {
                stockrepository.findAll().forEach(stock::add);
            } else {
                //stockrepository.findByStatutContainingIgnoreCase(keyword).forEach(stock::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("stock", stock);

            model.addAttribute("produitRef", produitRef);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "/home";
    }

}
