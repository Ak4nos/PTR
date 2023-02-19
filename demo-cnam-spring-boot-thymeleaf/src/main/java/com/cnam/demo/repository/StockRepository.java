package com.cnam.demo.repository;


import com.cnam.demo.entity.ProduitRef;
import com.cnam.demo.entity.Statut;
import com.cnam.demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    //List <Stock> findByStatutContainingIgnoreCase(String keyword);
}
