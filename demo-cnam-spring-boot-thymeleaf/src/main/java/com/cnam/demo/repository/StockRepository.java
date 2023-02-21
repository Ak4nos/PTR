package com.cnam.demo.repository;


import com.cnam.demo.entity.Statut;
import com.cnam.demo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface StockRepository extends JpaRepository<Stock, Integer> {

    List<Stock> findByDateFabrication(Date keyword);
    List<Stock> findByStatut (Statut statut);
}
