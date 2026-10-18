package com.pizzeria.pedidos_service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtFilter jwtFilter
    ) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/error").permitAll()

                // Crear pedidos sin iniciar sesión
                .requestMatchers(
                    HttpMethod.POST,
                    "/pedidos/crear"
                ).permitAll()

                // Consultar el seguimiento sin iniciar sesión
                .requestMatchers(
                    HttpMethod.GET,
                    "/pedidos/seguimiento/**"
                ).permitAll()

                // Editar y eliminar pedidos: solo ADMIN
                .requestMatchers(
                    HttpMethod.PUT,
                    "/pedidos/{id}"
                ).hasRole("ADMIN")

                .requestMatchers(
                    HttpMethod.DELETE,
                    "/pedidos/**"
                ).hasRole("ADMIN")

                // Resto de las operaciones de pedidos
                .requestMatchers("/pedidos/**")
                .hasAnyRole("ADMIN", "COCINERO", "REPARTIDOR")

                .anyRequest().authenticated()
            )
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}