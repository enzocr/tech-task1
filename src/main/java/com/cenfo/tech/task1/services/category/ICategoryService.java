package com.cenfo.tech.task1.services.category;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryService {

    ResponseEntity<Category> register(Category category);

    ResponseEntity<List<Category>> getAll();

    ResponseEntity<List<Product>>getAllProductsByCategory(Long id);

    ResponseEntity<Category> getById(Long id);

    ResponseEntity<Category> update(Long id, Category category);

    void delete(Long id);

}

