package com.pizzeria.pedidos_service.service;

import com.pizzeria.pedidos_service.dto.PedidoDTO;
import com.pizzeria.pedidos_service.entidades.Pedido;
import com.pizzeria.pedidos_service.repository.PedidoRepository;
import com.pizzeria.pedidos_service.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(
                pedidoDTO.getCliente(),
                pedidoDTO.getDireccionEntrega(),
                pedidoDTO.getTotal()
        );
        return pedidoRepository.save(pedido);
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
    }

    @Override
    public void eliminarPedido(Long id) {
        pedidoRepository.findById(id).ifPresent(pedido -> pedidoRepository.delete(pedido));
    }

    @Override
    public Pedido actualizarEstado(Long id, String nuevoEstado) {
        Pedido pedido = buscarPorId(id); // <-- importante, evita error 409
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido); // <-- este save() no genera conflicto
    }
    
}
