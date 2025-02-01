package com.cenfo.tech.task1.response;

import com.cenfo.tech.task1.entity.User;
import lombok.Data;

@Data
public class LogInResponse {
    private String token;

    private User authUser;

    private long expiresIn;
}
