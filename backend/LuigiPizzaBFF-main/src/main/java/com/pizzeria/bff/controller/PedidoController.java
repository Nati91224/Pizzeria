package com.pizzeria.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pizzeria.bff.dto.PedidoDTO;
import com.pizzeria.bff.service.PedidoService;
import java.util.List;

@RestController
@RequestMapping("/bff/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/crear")
    public ResponseEntity<PedidoDTO> crear(@RequestBody PedidoDTO pedidoDTO) throws Exception {
        return pedidoService.crearPedido(pedidoDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity<List> listar(
            @RequestHeader(value = "Authorization", required = false) String auth) throws Exception {
        return pedidoService.listarPedidos(auth);
    }

    @PutMapping("/estado/{id}")
    public ResponseEntity<PedidoDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado,
            @RequestHeader(value = "Authorization", required = false) String auth) throws Exception {
        return pedidoService.actualizarEstado(id, estado, auth);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String auth) throws Exception {
        return pedidoService.eliminarPedido(id, auth);
    }

    @PutMapping("/actualizar/{id}")
public ResponseEntity<PedidoDTO> actualizar(@PathVariable Long id,
        @RequestBody PedidoDTO pedidoDTO,
        @RequestHeader(value = "Authorization", required = false) String auth) throws Exception {
    return pedidoService.actualizarPedido(id, pedidoDTO, auth);
}
}