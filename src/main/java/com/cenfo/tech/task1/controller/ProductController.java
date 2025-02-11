package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.http.GlobalHandlerResponse;
import com.cenfo.tech.task1.response.http.MetaResponse;
import com.cenfo.tech.task1.services.category.ICategoryService;
import com.cenfo.tech.task1.services.product.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import request.RequestProduct;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;
    private final ICategoryService categoryService;

    public ProductController(IProductService productService, ICategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> registerProduct(@RequestBody RequestProduct requestProduct,
                                             HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.CREATED.name(),
                productService.register(buildProductDTO(requestProduct)),
                HttpStatus.CREATED,
                request
        );

    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        return getPaginatedResponse(productService.getAll(page, size), request);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'USER')")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> getProductById(@PathVariable Long id, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                productService.getById(id),
                HttpStatus.OK, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id,
                                           @RequestBody RequestProduct requestProduct,
                                           HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                productService.update(id, buildProductDTO(requestProduct)),
                HttpStatus.OK, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> getPaginatedResponse(Page<?> page, HttpServletRequest request) {
        if (page.isEmpty()) {
            return new GlobalHandlerResponse().handleResponse(HttpStatus.NO_CONTENT.name(), HttpStatus.OK, request);
        }

        MetaResponse metaResponse = new MetaResponse(
                request.getRequestURL().toString(),
                request.getMethod(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getSize()
        );

        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(), page.getContent(), HttpStatus.OK, metaResponse, request);
    }

    private Product buildProductDTO(RequestProduct requestProduct) {
        Category category = null;
        if (requestProduct.categoryId() != null) {
            category = categoryService.getById(requestProduct.categoryId());
        }
        Product product = new Product();
        product.setName(requestProduct.name());
        product.setDescription(requestProduct.description());
        product.setPrice(requestProduct.price());
        product.setStockQuantity(requestProduct.stockQuantity());
        product.setCategory(category);
        return product;
    }
}
