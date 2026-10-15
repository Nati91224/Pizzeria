package com.pizzeria.pedidos_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final Key key;
    private final long expiracionMs;

   public JwtService(@Value("${jwt.secret:clave-secreta-de-prueba-para-pizzeria-con-mas-de-32-caracteres}") String secret,
                  @Value("${jwt.expiracion-ms:28800000}") long expiracionMs) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.expiracionMs = expiracionMs;
}

    public String generarToken(String username, String rol) {
        Date ahora = new Date();
        return Jwts.builder()
                .setSubject(username)
                .claim("rol", rol)
                .setIssuedAt(ahora)
                .setExpiration(new Date(ahora.getTime() + expiracionMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims leerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}