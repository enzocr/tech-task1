package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestBody Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(product);
        }
        return productService.register(product);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
