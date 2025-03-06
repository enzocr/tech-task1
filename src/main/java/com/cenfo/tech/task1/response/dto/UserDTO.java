package com.cenfo.tech.task1.response.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String role;
    private String createdAt;
    private String updatedAt;
}
