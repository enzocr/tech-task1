package com.cenfo.tech.task1.controller;


import com.cenfo.tech.task1.entity.User;
import com.cenfo.tech.task1.request.RequestLogIn;
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
    public ResponseEntity<LogInResponse> authenticate(@Valid @RequestBody RequestLogIn requestLogIn) {
        User authenticatedUser = authService.authenticate(requestLogIn.email(), requestLogIn.password());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LogInResponse logInResponse = LogInResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .authUser(authenticatedUser)
                .build();
        return ResponseEntity.ok(logInResponse);
    }



}
