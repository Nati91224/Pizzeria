package com.pizzeria.pedidos_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App.
 */
@SpringBootTest
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void testApp() {
        assertTrue(true);
    }

    @Test
    public void contextLoads() {
        // Prueba básica para verificar que el contexto de Spring Boot se levanta
        // correctamente
        assertTrue(true);
    }
}