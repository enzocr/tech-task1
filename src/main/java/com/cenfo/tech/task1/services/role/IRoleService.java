package com.cenfo.tech.task1.services.role;

import com.cenfo.tech.task1.entity.Role;
import com.cenfo.tech.task1.entity.RoleEnum;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IRoleService {
    ResponseEntity<?> register(Role role);
    Optional<Role> findByName(RoleEnum name);
}
