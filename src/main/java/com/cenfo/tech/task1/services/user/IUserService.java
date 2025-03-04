package com.cenfo.tech.task1.services.user;

import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.response.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {
    ResponseEntity<?> register(User user);
    Optional<User> findByEmail(String email);
    Page<UserDTO> getAllPaginated(int page, int size);
}
