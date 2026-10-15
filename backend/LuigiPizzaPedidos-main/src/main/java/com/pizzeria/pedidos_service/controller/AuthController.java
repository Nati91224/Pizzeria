package com.pizzeria.pedidos_service.controller;

import com.pizzeria.pedidos_service.entidades.Usuario;
import com.pizzeria.pedidos_service.repository.UsuarioRepository;
import com.pizzeria.pedidos_service.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token, String rol) {}

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.username()).orElse(null);

        if (usuario == null || !passwordEncoder.matches(request.password(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos");
        }

        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol().name());
        return ResponseEntity.ok(new LoginResponse(token, usuario.getRol().name()));
    }
}