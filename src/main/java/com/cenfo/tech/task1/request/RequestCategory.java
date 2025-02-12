package com.cenfo.tech.task1.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record RequestCategory(@NotNull(message = "Name is required") String name,
                              @NotNull(message = "Description is required") String description,
                              List<RequestProduct> products) {
}
