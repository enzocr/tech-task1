package com.cenfo.tech.task1.services.user;

import com.cenfo.tech.task1.entity.*;
import com.cenfo.tech.task1.repository.IRoleRepository;
import com.cenfo.tech.task1.repository.IUserRepository;
import com.cenfo.tech.task1.request.RequestUpdateUser;
import com.cenfo.tech.task1.response.dto.UserDTO;
import com.cenfo.tech.task1.utils.UtilsDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository,
                       IRoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerAdmin(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EntityNotFoundException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> role = roleRepository.findByName(RoleEnum.SUPER_ADMIN);

        if (role.isEmpty()) {
            throw new EntityNotFoundException();
        }
        user.setRole(role.get());
        UtilsDTO.toUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO register(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new EntityNotFoundException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> role = roleRepository.findByName(RoleEnum.USER);

        if (role.isEmpty()) {
            throw new EntityNotFoundException();
        }
        user.setRole(role.get());
        return UtilsDTO.toUserDTO(userRepository.save(user));
    }

    @Override
    public ResponseEntity<?> signUp(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already registered");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> role = roleRepository.findByName(RoleEnum.USER);

        if (role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Role not found");
        }
        user.setRole(role.get());
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<UserDTO> getAllPaginated(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must be 0 or greater.");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1.");
        }
        long totalCategories = userRepository.count();
        int maxPages = (int) Math.ceil((double) totalCategories / size);

        if (page >= maxPages) {
            page = maxPages - 1;
        }

        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));

        return userPage
                .stream()
                .filter(user -> !user.getEmail().equals(currentUserEmail))
                .map(UtilsDTO::toUserDTO)
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> new PageImpl<>(list, userPage.getPageable(), userPage.getTotalElements())));
    }

    @Transactional
    @Override
    public UserDTO update(RequestUpdateUser userNewInfo) {
        User userToUpdate = userRepository.findByEmail(userNewInfo.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found " + userNewInfo.email()));
        userToUpdate.setName(userNewInfo.name());
        return UtilsDTO.toUserDTO(userRepository.save(userToUpdate));
    }

    @Override
    public ResponseEntity<?> delete(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
        } else throw new EntityNotFoundException("User not found with email: " + email);
        return ResponseEntity.ok("User deleted successfully");
    }


}
