package com.pizzeria.pedidos_service.service;

import com.pizzeria.pedidos_service.entidades.Pedido;
import com.pizzeria.pedidos_service.dto.PedidoDTO;

import java.util.List;

public interface PedidoService {
    Pedido crearPedido(PedidoDTO pedidoDTO);
    List<Pedido> listarPedidos();
    Pedido buscarPorId(Long id);
    void eliminarPedido(Long id);
    Pedido actualizarEstado(Long id, String nuevoEstado);
}
