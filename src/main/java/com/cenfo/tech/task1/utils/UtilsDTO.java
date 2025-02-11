package com.cenfo.tech.task1.utils;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import com.cenfo.tech.task1.response.dto.ProductDTO;

public class UtilsDTO {

    public static CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .description(category.getDescription())
                .name(category.getName())
                .build();
    }

    public static ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(toCategoryDTO(product.getCategory()))
                .build();
    }
}
