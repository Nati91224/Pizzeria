package com.pizzeria.pedidos_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pizzeria.pedidos_service.entidades.Pedido;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCliente(String cliente);
}
