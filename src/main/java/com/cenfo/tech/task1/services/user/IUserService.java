package com.cenfo.tech.task1.services.user;

import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.request.RequestUpdateUser;
import com.cenfo.tech.task1.response.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IUserService {
    UserDTO register(User user);
    void registerAdmin(User user);
    ResponseEntity<?> signUp(User user);
    Optional<User> findByEmail(String email);
    Page<UserDTO> getAllPaginated(int page, int size);
    UserDTO update(RequestUpdateUser user);
    ResponseEntity<?> delete(String email);
}
