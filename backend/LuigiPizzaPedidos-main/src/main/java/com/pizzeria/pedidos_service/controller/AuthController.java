package com.pizzeria.pedidos_service.controller;

import com.pizzeria.pedidos_service.entidades.Rol;
import com.pizzeria.pedidos_service.entidades.Usuario;
import com.pizzeria.pedidos_service.repository.UsuarioRepository;
import com.pizzeria.pedidos_service.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token, String rol) {}
    public record RegisterRequest(String username, String password, String rol) {}

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
        Optional<Usuario> optUsuario = usuarioRepository.findByUsername(request.username());

        if (optUsuario.isEmpty() || !passwordEncoder.matches(request.password(), optUsuario.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        Usuario usuario = optUsuario.get();
        String token = jwtService.generarToken(usuario.getUsername(), usuario.getRol().name());
        return ResponseEntity.ok(new LoginResponse(token, usuario.getRol().name()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Usuario ya existe"));
        }

        Rol rol;
        try {
            rol = Rol.valueOf(request.rol());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Rol inválido. Usar ADMIN, COCINERO o REPARTIDOR"));
        }

        Usuario nuevo = new Usuario(request.username(), passwordEncoder.encode(request.password()), rol);
        usuarioRepository.save(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Usuario creado"));
    }

    // temporal: resetear contraseña de un usuario existente
    @PutMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody RegisterRequest request) {
        Optional<Usuario> optUsuario = usuarioRepository.findByUsername(request.username());
        if (optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Usuario no existe"));
        }
        Usuario usuario = optUsuario.get();
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
    }
}