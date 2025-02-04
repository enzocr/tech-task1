package com.cenfo.tech.task1.services.category;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.repository.ICategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<Category> register(Category category) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryRepository.save(category));
    }

    @Override
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryRepository.findAll());
    }

    @Override
    public ResponseEntity<Category> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public ResponseEntity<List<Product>> getAllProductsByCategory(Long id) {
        return categoryRepository.findById(id)
                .map(category -> ResponseEntity.ok(category.getProducts()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList()));
    }

    @Transactional
    @Override
    public ResponseEntity<Category> update(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    category.setId(id);
                    return ResponseEntity.ok(categoryRepository.save(category));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
