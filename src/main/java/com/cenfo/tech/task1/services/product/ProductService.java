package com.cenfo.tech.task1.services.product;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.repository.IProductRepository;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import com.cenfo.tech.task1.utils.UtilsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import request.RequestUpdateProduct;

import java.util.Optional;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;

    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public ProductDTO register(Product product) {
        try {
            return UtilsDTO.toProductDTO(productRepository.save(product));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<Product> getAll(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be 0 or greater.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }

        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));
        if (productPage.isEmpty()) {
            throw new EntityNotFoundException("No products found on page " + page + " with size " + size);
        }
        return productPage;
    }


    @Override
    public ProductDTO getById(Long id) {
        return productRepository.findById(id)
                .map(UtilsDTO::toProductDTO)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Transactional
    @Override
    public ProductDTO update(Long id, RequestUpdateProduct productNewInfo) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));

        productToUpdate.setName(productNewInfo.name());
        productToUpdate.setDescription(productNewInfo.description());
        productToUpdate.setPrice(productNewInfo.price());
        productToUpdate.setStockQuantity(productNewInfo.stockQuantity());

        return UtilsDTO.toProductDTO(productRepository.save(productToUpdate));
    }

    @Override
    public void delete(Long id) {
        Optional.of(productRepository.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .ifPresentOrElse(
                        product -> productRepository.deleteById(id),
                        () -> {
                            throw new EntityNotFoundException("Product not found with id: " + id);
                        }
                );
    }
}
