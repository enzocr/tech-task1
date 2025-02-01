package com.cenfo.tech.task1.controller;


import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.response.LogInResponse;
import com.cenfo.tech.task1.security.AuthService;
import com.cenfo.tech.task1.security.JwtService;
import com.cenfo.tech.task1.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final IUserService userService;
    private final AuthService authService;
    private final JwtService jwtService;


    public AuthController(IUserService userService, AuthService authService, JwtService jwtService) {
        this.userService = userService;
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/logIn")
    public ResponseEntity<LogInResponse> authenticate(@RequestBody User user) {
        User authenticatedUser = authService.authenticate(user.getUsername(), user.getPassword());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LogInResponse loginResponse = new LogInResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        Optional<User> foundedUser = userService.findByEmail(user.getEmail());

        foundedUser.ifPresent(loginResponse::setAuthUser);
        return ResponseEntity.ok(loginResponse);
    }

}
