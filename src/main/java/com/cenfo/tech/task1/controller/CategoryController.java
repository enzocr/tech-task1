package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import com.cenfo.tech.task1.response.http.GlobalHandlerResponse;
import com.cenfo.tech.task1.response.http.MetaResponse;
import com.cenfo.tech.task1.services.category.ICategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.cenfo.tech.task1.request.RequestCategory;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerCategory(@Valid @RequestBody RequestCategory requestCategory, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.CREATED.name(),
                categoryService.register(new Category(requestCategory.name(), requestCategory.description())),
                HttpStatus.OK, request);
    }


    @PostMapping("/registerCategories")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<?> registerCategories(@Valid @RequestBody List<RequestCategory> requestCategories, HttpServletRequest request) {
        for (RequestCategory requestCategory : requestCategories) {

            Category category = new Category(requestCategory.name(), requestCategory.description());
            if (requestCategory.products() != null) {
                List<Product> products = requestCategory.products().stream()
                        .map(requestProduct -> new Product(
                                requestProduct.name(),
                                requestProduct.description(),
                                requestProduct.price(),
                                requestProduct.stockQuantity(),
                                category
                        ))
                        .toList();

                category.setProducts(products);
            }
            categoryService.register(category);
        }

        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.CREATED.name(),
                "Categories successfully registered",
                HttpStatus.OK, request);
    }


    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllCategories(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Page<CategoryDTO> categoryPage = categoryService.getAll(page, size);
        return getPaginatedResponse(categoryPage, request);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getCategoryById(@PathVariable Long id, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                categoryService.getByIdDTO(id),
                HttpStatus.OK, request);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping("/products/{categoryId}")
    @ResponseBody
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long categoryId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Page<ProductDTO> productsByCategoryPage = categoryService.getAllProductsByCategory(categoryId, page, size);
        return getPaginatedResponse(productsByCategoryPage, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody RequestCategory category, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(HttpStatus.OK.name(),
                categoryService.update(id, category),
                HttpStatus.OK, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
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
