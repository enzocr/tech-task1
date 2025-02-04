package com.cenfo.tech.task1.config;

import com.cenfo.tech.task1.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String SUPER_ADMIN = "SUPER_ADMIN";
    private final String USER = "USER";

    private final String LOG_IN_URI = "/api/users/logIn";
    private final String BASE_URI_PRODUCTS = "/api/products";
    private final String BASE_URI_CATEGORIES = "/api/categories";

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, LOG_IN_URI).permitAll()
                        .requestMatchers(HttpMethod.POST, BASE_URI_PRODUCTS).hasRole(SUPER_ADMIN)
                        .requestMatchers(HttpMethod.PUT, BASE_URI_PRODUCTS, BASE_URI_CATEGORIES).hasRole(SUPER_ADMIN)
                        .requestMatchers(HttpMethod.DELETE, BASE_URI_PRODUCTS, BASE_URI_CATEGORIES).hasRole(SUPER_ADMIN)
                        .requestMatchers(HttpMethod.GET, BASE_URI_PRODUCTS, BASE_URI_CATEGORIES).hasAnyRole(SUPER_ADMIN, USER)
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
