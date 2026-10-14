package com.pizzeria.productos_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pizzeria.productos_service.entidades.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}
