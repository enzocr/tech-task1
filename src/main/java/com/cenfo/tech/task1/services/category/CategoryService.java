package com.cenfo.tech.task1.services.category;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.repository.ICategoryRepository;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import com.cenfo.tech.task1.utils.UtilsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.RequestUpdateCategory;

@Service
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    @Override
    public CategoryDTO register(Category category) {
        try {
            return UtilsDTO.toCategoryDTO(categoryRepository.save(category));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Category> getAll(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be 0 or greater.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }

        Page<Category> categoryPage = categoryRepository.findAll(PageRequest.of(page, size));
        if (categoryPage.isEmpty()) {
            throw new EntityNotFoundException("No categories found on page " + page + " with size " + size);
        }
        return categoryPage;
    }


    @Override
    public CategoryDTO getByIdDTO(Long id) {
        return UtilsDTO.toCategoryDTO(getById(id));
    }

    @Override
    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));

    }

    @Override
    public Page<Product> getAllProductsByCategory(Long categoryId, int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be 0 or greater.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }

        Page<Product> productPage = categoryRepository.findProductsByCategoryId(categoryId, PageRequest.of(page, size));
        if (productPage.isEmpty()) {
            throw new EntityNotFoundException("No products found for category " + categoryId + " on page " + page + " with size " + size);
        }
        return productPage;
    }

    @Transactional
    @Override
    public CategoryDTO update(Long id, RequestUpdateCategory categoryNewInfo) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        categoryToUpdate.setName(categoryNewInfo.name());
        categoryToUpdate.setDescription(categoryNewInfo.description());
        return UtilsDTO.toCategoryDTO(categoryRepository.save(categoryToUpdate));
    }

    @Override
    public void delete(Long id) {
        if (categoryRepository.findById(id).isPresent()) {
            categoryRepository.deleteById(id);
        } else throw new EntityNotFoundException("Category not found with id: " + id);
    }
}
