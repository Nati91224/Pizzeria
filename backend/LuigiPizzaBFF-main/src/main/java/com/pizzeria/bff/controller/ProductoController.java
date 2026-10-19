package com.pizzeria.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pizzeria.bff.dto.ProductoDTO;
import com.pizzeria.bff.service.ProductoService;
import java.util.List;

@RestController
@RequestMapping("/bff/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crear(@RequestBody ProductoDTO productoDTO) throws Exception {
        return productoService.crearProducto(productoDTO);
    }

    @GetMapping("/listar")
    public ResponseEntity<List> listar() throws Exception {
        return productoService.listarProductos();
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String auth) throws Exception {
        return productoService.eliminarProducto(id, auth);
    }
}