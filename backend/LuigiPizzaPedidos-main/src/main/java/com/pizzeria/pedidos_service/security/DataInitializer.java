package com.pizzeria.pedidos_service.security;

import com.pizzeria.pedidos_service.entidades.Rol;
import com.pizzeria.pedidos_service.entidades.Usuario;
import com.pizzeria.pedidos_service.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner crearUsuarios(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            crear(repo, encoder, "admin", "admin123", Rol.ADMIN);
            crear(repo, encoder, "cocinero", "cocina123", Rol.COCINERO);
            crear(repo, encoder, "repartidor", "reparto123", Rol.REPARTIDOR);
        };
    }

    private void crear(UsuarioRepository repo, PasswordEncoder encoder,
                   String user, String pass, Rol rol) {
    Usuario u = repo.findByUsername(user).orElse(new Usuario());
    u.setUsername(user);
    u.setPassword(encoder.encode(pass));
    u.setRol(rol);
    repo.save(u);
}
}