package com.cenfo.tech.task1.repository;

import com.cenfo.tech.task1.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
