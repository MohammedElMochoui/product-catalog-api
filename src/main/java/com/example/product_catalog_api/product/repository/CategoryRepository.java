package com.example.product_catalog_api.product.repository;

import com.example.product_catalog_api.product.model.Category;
import com.example.product_catalog_api.product.model.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryEnum name);
}
