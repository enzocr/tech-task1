package com.cenfo.tech.task1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> products;

    public Category() {
    }

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Category(@NotNull(message = "Name is required") String name,
                    @NotNull(message = "Description is required") String description,
                    List<Product> products) {
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
