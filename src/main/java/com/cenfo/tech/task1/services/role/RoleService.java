package com.cenfo.tech.task1.services.role;

import com.cenfo.tech.task1.entity.Role;
import com.cenfo.tech.task1.entity.RoleEnum;
import com.cenfo.tech.task1.repository.IRoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public ResponseEntity<?> register(Role role) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleRepository.save(role));
    }

    @Override
    public Optional<Role> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
}
