package com.pizzeria.bff.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.pizzeria.bff.dto.PedidoDTO;

public interface PedidoService {
    ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception;
    ResponseEntity<List> listarPedidos() throws Exception;
    ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado) throws Exception;
    ResponseEntity<Void> eliminarPedido(Long id) throws Exception;
}
