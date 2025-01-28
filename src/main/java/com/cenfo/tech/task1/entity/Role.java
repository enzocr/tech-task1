package com.cenfo.tech.task1.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // ROLE_SUPER_ADMIN, ROLE_USER

}

