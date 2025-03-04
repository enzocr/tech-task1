package com.cenfo.tech.task1.request;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;

@Builder
public record RequestProduct(@NotNull(message = "Name is required") String name,
                             @NotNull(message = "Description is required") String description,
                             @NotNull(message = "Price is required") Double price,
                             @NotNull(message = "Stock Quantity is required") Integer stockQuantity,
                             @NotNull(message = "Category is required") Long categoryId) {
}
