package com.cenfo.tech.task1.utils;

import com.cenfo.tech.task1.entity.Category;
import com.cenfo.tech.task1.entity.Product;
import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.response.dto.CategoryDTO;
import com.cenfo.tech.task1.response.dto.ProductDTO;
import com.cenfo.tech.task1.response.dto.UserDTO;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UtilsDTO {

    public static CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .description(category.getDescription())
                .name(category.getName())
                .products(category.getProducts() != null ?
                        UtilsDTO.mapProductListEntityToProductListDTO(category.getProducts()) : List.of())
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
                .category(toCategoryWithNoProductsDTO(product.getCategory()))
                .build();
    }

    public static UserDTO toUserDTO(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .role(user.getRole().getName().name())
                .createdAt(user.getCreatedAt() != null ? formatter.format(user.getCreatedAt().toInstant()) : null)
                .updatedAt(user.getUpdatedAt() != null ? formatter.format(user.getUpdatedAt().toInstant()) : null)
                .build();
    }
    public static List<ProductDTO> mapProductListEntityToProductListDTO(List<Product> list) {
        if (list == null) {
            return List.of();
        }
        return list.stream().map(UtilsDTO::toProductDTO).collect(Collectors.toList());
    }

    public static List<CategoryDTO> mapCategoryListEntityToCategoryListDTO(List<Category> list) {
        if (list == null) {
            return List.of();
        }
        return list.stream().map(UtilsDTO::toCategoryWithNoProductsDTO).collect(Collectors.toList());
    }
}
