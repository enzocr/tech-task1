package com.cenfo.tech.task1.response.http;

import com.cenfo.tech.task1.entity.User;
import lombok.Builder;

@Builder
public record LogInResponse(String token, long expiresIn, User authUser) {}
