package com.cenfo.tech.task1.controller;


import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.request.RequestUser;
import com.cenfo.tech.task1.response.http.LogInResponse;
import com.cenfo.tech.task1.services.security.AuthService;
import com.cenfo.tech.task1.services.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/logIn")
    public ResponseEntity<LogInResponse> authenticate(@Valid @RequestBody RequestUser userRequest) {
        User authenticatedUser = authService.authenticate(userRequest.email(), userRequest.password());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LogInResponse logInResponse = LogInResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();
        return ResponseEntity.ok(logInResponse);
    }

}
