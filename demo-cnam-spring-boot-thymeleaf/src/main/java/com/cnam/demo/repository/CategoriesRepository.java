package com.cnam.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.cnam.demo.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
  List<Categories> findByDesignationContainingIgnoreCase(String keyword);


}
