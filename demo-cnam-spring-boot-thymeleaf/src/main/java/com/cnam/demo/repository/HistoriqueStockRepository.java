package com.cnam.demo.repository;

import com.cnam.demo.entity.HistoriqueStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface HistoriqueStockRepository extends JpaRepository<HistoriqueStock, Integer> {
    List<HistoriqueStock> findByDateCreation (Date dateCreation);
}
