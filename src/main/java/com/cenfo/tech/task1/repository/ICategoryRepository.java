package com.cenfo.tech.task1.repository;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    @EntityGraph(attributePaths = "products")
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Page<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}
