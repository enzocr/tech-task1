package com.cenfo.tech.task1.response;

import lombok.Builder;

@Builder
public record LogInResponse(String token, long expiresIn) {}
