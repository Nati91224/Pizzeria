package com.pizzeria.bff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pizzeria.bff.dto.PedidoDTO;
import com.pizzeria.bff.requester.PedidoRequester;
import java.util.List;

@Service
public class PedidoServiceImp implements PedidoService {

    @Autowired
    private PedidoRequester pedidoRequester;

    @Override
    public ResponseEntity<PedidoDTO> crearPedido(PedidoDTO pedidoDTO) throws Exception {
        return pedidoRequester.crearPedido(pedidoDTO);
    }

    @Override
    public ResponseEntity<List> listarPedidos() throws Exception {
        return pedidoRequester.listarPedidos();
    }

    @Override
    public ResponseEntity<PedidoDTO> actualizarEstado(Long id, String estado) throws Exception {
        return pedidoRequester.actualizarEstado(id, estado);
    }
	@Override
	public ResponseEntity<Void> eliminarPedido(Long id) throws Exception {
		return pedidoRequester.eliminarPedido(id);
	}
}
