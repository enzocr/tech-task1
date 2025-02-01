package com.cenfo.tech.task1.repository;

import com.cenfo.tech.task1.entity.Role;
import com.cenfo.tech.task1.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}
