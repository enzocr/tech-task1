package com.cenfo.tech.task1.services.product;

import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import org.springframework.data.domain.Page;
import request.RequestUpdateProduct;

public interface IProductService {
    ProductDTO register(Product product);

    Page<Product> getAll(int page, int size);

    ProductDTO getById(Long id);

    ProductDTO update(Long id, RequestUpdateProduct product);

    void delete(Long id);
}
