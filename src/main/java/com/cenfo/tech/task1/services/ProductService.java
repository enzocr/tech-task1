package com.cenfo.tech.task1.services;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.repository.IProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public ResponseEntity<Product> register(Product product) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.save(product));
    }

    @Override
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findAll());
    }

    @Override
    public ResponseEntity<Product> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findById(id).orElse(null));
    }

    @Transactional
    @Override
    public ResponseEntity<Product> update(Long id, Product product) {

        return productRepository.findById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
