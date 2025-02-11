package com.cenfo.tech.task1.response.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryDTO {
    private String name;
    private String description;
}
