
package com.pizzeria.bff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pizzeria.bff.dto.PedidoDTO;
import com.pizzeria.bff.requester.PedidoRequester;
import java.util.List;
import java.util.Map;

@Service
public class PedidoServiceImp implements PedidoService {

    @Autowired
    private PedidoRequester pedidoRequester;

    @Override
    public ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception {
        return pedidoRequester.crearPedido(pedidoDTO);
    }

    @Override
    public ResponseEntity<List> listarPedidos(String auth) throws Exception {
        return pedidoRequester.listarPedidos(auth);
    }

    @Override
    public ResponseEntity<PedidoDTO> actualizarPedido(Long id, PedidoDTO pedidoDTO, String auth) throws Exception {
        return pedidoRequester.actualizarPedido(id, pedidoDTO, auth);
    }

    @Override
    public ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado, String auth) throws Exception {
        return pedidoRequester.actualizarEstado(id, estado, auth);
    }

    @Override
    public ResponseEntity<Void> eliminarPedido(Long id, String auth) throws Exception {
        return pedidoRequester.eliminarPedido(id, auth);
    }

    @Override
    public ResponseEntity<Map> seguimiento(Long id) throws Exception {
        return pedidoRequester.seguimiento(id);
    }
}
