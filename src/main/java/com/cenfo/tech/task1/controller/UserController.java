package com.cenfo.tech.task1.controller;

import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.request.RequestUpdateUser;
import com.cenfo.tech.task1.request.RequestUser;
import com.cenfo.tech.task1.response.http.GlobalHandlerResponse;
import com.cenfo.tech.task1.response.http.MetaResponse;
import com.cenfo.tech.task1.services.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;


    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RequestUser userRequest, HttpServletRequest request) {
        if (userService.findByEmail(userRequest.email()).isPresent()) {
            return new GlobalHandlerResponse().handleResponse(
                    "Email already registered",
                    HttpStatus.CONFLICT,
                    request);
        }

        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setEmail(userRequest.email());
        newUser.setPassword(userRequest.password());

        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                userService.register(newUser),
                HttpStatus.OK, request);

    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestUser userRequest, HttpServletRequest request) {
        if (userService.findByEmail(userRequest.email()).isPresent()) {
            return new GlobalHandlerResponse().handleResponse(
                    "Email already registered",
                    HttpStatus.CONFLICT,
                    request);
        }

        User newUser = new User();
        newUser.setName(userRequest.name());
        newUser.setEmail(userRequest.email());
        newUser.setPassword(userRequest.password());

        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                userService.signUp(newUser),
                HttpStatus.OK, request);

    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/paginated")
    @ResponseBody
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        return getPaginatedResponse(userService.getAllPaginated(page, size), request);
    }

    private ResponseEntity<?> getPaginatedResponse(Page<?> page, HttpServletRequest request) {
        if (page.isEmpty()) {
            return new GlobalHandlerResponse().handleResponse(HttpStatus.NO_CONTENT.name(), HttpStatus.OK, request);
        }

        MetaResponse metaResponse = new MetaResponse(
                request.getRequestURL().toString(),
                request.getMethod(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getSize()
        );

        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(), page.getContent(), HttpStatus.OK, metaResponse, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PatchMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody RequestUpdateUser requestUpdateUser,
                                        HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                userService.update(requestUpdateUser),
                HttpStatus.OK, request);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email,  HttpServletRequest request) {
        return new GlobalHandlerResponse().handleResponse(
                HttpStatus.OK.name(),
                userService.delete(email),
                HttpStatus.OK, request);
    }

}
