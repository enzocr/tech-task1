package com.cenfo.tech.task1.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO {
    private String name;
    private String description;
    private List<ProductDTO> products;
}
