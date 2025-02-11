package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import com.cenfo.tech.task1.response.http.GlobalHandlerResponse;
import com.cenfo.tech.task1.response.http.MetaResponse;
import com.cenfo.tech.task1.services.product.IProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import request.RequestUpdateProduct;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<?> registerProduct(@RequestBody Product product, HttpServletRequest request) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            return new GlobalHandlerResponse().handleResponse(
                    HttpStatus.BAD_REQUEST.name(),
                    "Product category or category ID is required",
                    HttpStatus.BAD_REQUEST,
                    request
            );
        }

        ProductDTO productDTO = productService.register(product);
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.CREATED.name(),
                productDTO,
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
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody RequestUpdateProduct product, HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                productService.update(id, product),
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
}
