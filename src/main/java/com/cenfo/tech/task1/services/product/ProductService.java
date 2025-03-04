package com.cenfo.tech.task1.services.product;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.repository.IProductRepository;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import com.cenfo.tech.task1.utils.UtilsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<ProductDTO> getAllPaginated(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be 0 or greater.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }
        long totalCategories = productRepository.count();
        int maxPages = (int) Math.ceil((double) totalCategories / size);

        if (page >= maxPages) {
            page = maxPages - 1;
        }

        Page<Product> productPage = productRepository.findAll(PageRequest.of(page, size));

        return productPage.map(UtilsDTO::toProductDTO);
    }


    @Override
    public ProductDTO getById(Long id) {
        return productRepository.findById(id)
                .map(UtilsDTO::toProductDTO)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    @Transactional
    @Override
    public ProductDTO update(Long id, Product productNewInfo) {
        Product productToUpdate = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        productNewInfo.setId(id);
        if (productNewInfo.getCategory() == null) {
            productNewInfo.setCategory(productToUpdate.getCategory());
        }
        return UtilsDTO.toProductDTO(productRepository.save(productNewInfo));
    }

    @Override
    public void delete(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        } else throw new EntityNotFoundException("Product not found with id: " + id);
    }
}
