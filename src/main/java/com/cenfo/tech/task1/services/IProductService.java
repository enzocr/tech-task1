package com.cenfo.tech.task1.services;

import com.cenfo.tech.task1.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {
    ResponseEntity<Product> register(Product product);

    ResponseEntity<List<Product>> getAll();

    ResponseEntity<Product> getById(Long id);

    ResponseEntity<Product> update(Long id, Product product);

    void delete(Long id);
}
