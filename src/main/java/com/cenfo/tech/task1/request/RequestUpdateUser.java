package com.cenfo.tech.task1.request;

import jakarta.validation.constraints.NotNull;

public record RequestUpdateUser(@NotNull(message = "Email is required") String email,
                                @NotNull(message = "Name is required") String name) {
}
