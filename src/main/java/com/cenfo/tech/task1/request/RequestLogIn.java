package com.cenfo.tech.task1.request;

import jakarta.validation.constraints.NotNull;

public record RequestLogIn(@NotNull(message = "Email is required") String email,
                           @NotNull(message = "Password is required") String password) {
}
