package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    // Endpoint para registrar un producto
    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestBody Product product) {
        return productService.register(product);
    }

    // Endpoint para obtener todos los productos
    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAll();
    }

    // Endpoint para obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    // Endpoint para actualizar un producto
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }


//    @PreAuthorize("hasAuthority('SUPER-ADMIN-ROLE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
