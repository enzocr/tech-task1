package com.cenfo.tech.task1.response.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String rol;
    private String createdAt;
    private String updatedAt;
}
