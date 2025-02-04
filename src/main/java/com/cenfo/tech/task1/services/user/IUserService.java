package com.cenfo.tech.task1.services.user;

import com.cenfo.tech.task1.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {
    public ResponseEntity<?> register(User user);
    Optional<User> findByEmail(String email);
}
