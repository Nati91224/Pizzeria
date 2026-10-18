
package com.pizzeria.bff.requester;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.pizzeria.bff.dto.PedidoDTO;

public interface PedidoRequester {
    ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception;

    ResponseEntity<List> listarPedidos(String auth) throws Exception;

    ResponseEntity<PedidoDTO> actualizarPedido(Long id, PedidoDTO pedidoDTO, String auth) throws Exception;

    ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado, String auth) throws Exception;

    ResponseEntity<Void> eliminarPedido(Long id, String auth) throws Exception;

    ResponseEntity<Map> seguimiento(Long id) throws Exception;
}
