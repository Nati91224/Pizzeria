package com.pizzeria.pedidos_service.controller;

import com.pizzeria.pedidos_service.dto.PedidoDTO;
import com.pizzeria.pedidos_service.entidades.Pedido;
import com.pizzeria.pedidos_service.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/pedidos")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // 👉 Crear pedidoa
    @PostMapping("/crear")
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        Pedido pedidoCreado = pedidoService.crearPedido(pedidoDTO);
        return new ResponseEntity<>(pedidoCreado, HttpStatus.CREATED);
    }

    // 👉 Listar pedidos
    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = pedidoService.listarPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // 👉 Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return (pedido != null)
                ? new ResponseEntity<>(pedido, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // 👉 Actualizar estado
    @PutMapping("/actualizar/{id}")
public ResponseEntity<?> actualizarEstado(@PathVariable Long id,
                                          @RequestParam String estado,
                                          Authentication authentication) {
    Pedido pedido = pedidoService.buscarPorId(id);
    if (pedido == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    String rol = authentication.getAuthorities().iterator().next().getAuthority();
    String actual = String.valueOf(pedido.getEstado()).toUpperCase();
    String nuevo = estado.toUpperCase();

    boolean permitido = switch (rol) {
        case "ROLE_ADMIN" -> true;
        case "ROLE_COCINERO" -> actual.equals("SOLICITADO") && nuevo.equals("PREPARADO");
        case "ROLE_REPARTIDOR" -> actual.equals("PREPARADO") && nuevo.equals("ENTREGADO");
        default -> false;
    };

    if (!permitido) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Tu rol no puede hacer este cambio de estado");
    }

    try {
        return ResponseEntity.ok(pedidoService.actualizarEstado(id, estado));
    } catch (RuntimeException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

    // 👉 Eliminar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.noContent().build(); // 204 siempre
    }


}
