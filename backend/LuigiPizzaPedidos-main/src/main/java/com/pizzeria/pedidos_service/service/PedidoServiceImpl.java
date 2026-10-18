package com.pizzeria.pedidos_service.service;

import com.pizzeria.pedidos_service.dto.ItemPedidoDTO;
import com.pizzeria.pedidos_service.dto.PedidoDTO;
import com.pizzeria.pedidos_service.entidades.ItemPedido;
import com.pizzeria.pedidos_service.entidades.Pedido;
import com.pizzeria.pedidos_service.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
public Pedido crearPedido(PedidoDTO dto) {
    Pedido pedido = new Pedido(dto.getCliente(), dto.getDireccionEntrega(), dto.getTotal());
    pedido.setTelefono(dto.getTelefono());
    pedido.setNotas(dto.getNotas());
    if (dto.getItems() != null) {
        for (ItemPedidoDTO i : dto.getItems()) {
            ItemPedido item = new ItemPedido();
            item.setProductoId(i.getProductoId());
            item.setNombre(i.getNombre());
            item.setCantidad(i.getCantidad());
            item.setPrecioUnitario(i.getPrecioUnitario());
            item.setPedido(pedido);
            pedido.getItems().add(item);
        }
    }
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

   @Override
public Pedido actualizarPedido(Long id, PedidoDTO dto) {
    Pedido pedido = buscarPorId(id);
    pedido.setCliente(dto.getCliente());
    pedido.setTelefono(dto.getTelefono());
    pedido.setDireccionEntrega(dto.getDireccionEntrega());
    pedido.setNotas(dto.getNotas());
    pedido.setTotal(dto.getTotal());
    return pedidoRepository.save(pedido);
}
    
}
