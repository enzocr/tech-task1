package com.cenfo.tech.task1.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RequestUser(@NotNull(message = "Name is required") String name,
                          @NotNull(message = "Email is required") String email,
                          @NotNull(message = "Password is required") String password) {
}
