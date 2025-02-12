package com.cenfo.tech.task1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(@NotNull(message = "Name is required") String name,
                   @NotNull(message = "Description is required") String description,
                   @NotNull(message = "Price is required") Double price,
                   @NotNull(message = "Stock Quantity is required") Integer integer,
                   @NotNull(message = "Category is required") Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = integer;
        this.category = category;
    }
}
