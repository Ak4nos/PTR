package com.cnam.demo.repository;

import com.cnam.demo.entity.ProduitRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProduitRefRepository extends JpaRepository<ProduitRef, Integer> {
    List<ProduitRef> findByNomProduitContainingIgnoreCase(String keyword);

}
