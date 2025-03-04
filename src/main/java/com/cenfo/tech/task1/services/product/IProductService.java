package com.cenfo.tech.task1.services.product;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import org.springframework.data.domain.Page;

public interface IProductService {
    ProductDTO register(Product product);

    Page<ProductDTO> getAllPaginated(int page, int size);

    ProductDTO getById(Long id);

    ProductDTO update(Long id, Product product);

    void delete(Long id);
}
