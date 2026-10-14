package com.pizzeria.bff.requester;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.pizzeria.bff.dto.PedidoDTO;

public interface PedidoRequester {
    ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception;
    ResponseEntity<List> listarPedidos() throws Exception;
    ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado) throws Exception;
    ResponseEntity<Void> eliminarPedido(Long id) throws Exception;
}
