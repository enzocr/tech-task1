package com.cenfo.tech.task1.response;

import com.cenfo.tech.task1.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LogInResponse {
    private String token;

    @JsonIgnore
    private User authUser;

    private long expiresIn;
}
