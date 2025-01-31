package com.cenfo.tech.task1.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    public void setId(Long id) {
        this.id = id;
    }

}
