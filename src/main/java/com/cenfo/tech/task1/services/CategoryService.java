package com.cenfo.tech.task1.services;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

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
    public ResponseEntity<Category> update(Long id, Category category) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
