package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.http.GlobalHandlerResponse;
import com.cenfo.tech.task1.response.http.MetaResponse;
import com.cenfo.tech.task1.services.category.ICategoryService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import request.RequestUpdateCategory;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerCategory(@RequestBody Category category, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(HttpStatus.CREATED.name(), categoryService.register(category), HttpStatus.OK, request);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Page<Category> categoryPage = categoryService.getAll(page, size);
        return getPaginatedResponse(categoryPage, request);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getCategoryById(@PathVariable Long id, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                categoryService.getById(id),
                HttpStatus.OK, request);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping("/products/{categoryId}")
    @ResponseBody
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Page<Product> productsByCategoryPage = categoryService.getAllProductsByCategory(categoryId, page, size);
        return getPaginatedResponse(productsByCategoryPage, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody RequestUpdateCategory category, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(HttpStatus.OK.name(), categoryService.update(id, category), HttpStatus.OK, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> getPaginatedResponse(Page<?> page, HttpServletRequest request) {
        if (page.isEmpty()) {
            return new GlobalHandlerResponse().handleResponse(HttpStatus.NO_CONTENT.name(),
                    page.getContent(), HttpStatus.OK, request);
        }

        MetaResponse metaResponse = new MetaResponse(
                request.getRequestURL().toString(),
                request.getMethod(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getSize()
        );
        return new GlobalHandlerResponse().handleResponse(HttpStatus.OK.name(), page.getContent(), HttpStatus.OK, metaResponse, request);
    }

}
