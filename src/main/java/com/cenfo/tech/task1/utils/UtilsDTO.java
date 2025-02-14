package com.cenfo.tech.task1.utils;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import com.cenfo.tech.task1.response.dto.ProductDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UtilsDTO {

    public static CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .description(category.getDescription())
                .name(category.getName())
                .products(Optional.ofNullable(category.getProducts())
                        .map(UtilsDTO::mapProductListEntityToProductListDTO)
                        .orElse(List.of()))
                .build();
    }

    public static CategoryDTO toCategoryWithNoProductsDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .description(category.getDescription())
                .name(category.getName())
                .build();
    }

    public static ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }

    public static List<ProductDTO> mapProductListEntityToProductListDTO(List<Product> list) {
        return list.stream().map(UtilsDTO::toProductDTO).collect(Collectors.toList());
    }
}
