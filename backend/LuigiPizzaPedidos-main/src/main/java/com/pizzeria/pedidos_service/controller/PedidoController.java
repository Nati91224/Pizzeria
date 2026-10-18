package com.pizzeria.pedidos_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pizzeria.pedidos_service.dto.PedidoDTO;
import com.pizzeria.pedidos_service.entidades.Pedido;
import com.pizzeria.pedidos_service.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear")
    public ResponseEntity<Pedido> crear(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.crearPedido(pedidoDTO));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listar() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado));
    }
}