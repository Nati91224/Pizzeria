package com.pizzeria.productos_service.controller;

import com.pizzeria.productos_service.dto.ProductoDTO;
import com.pizzeria.productos_service.entidades.Producto;
import com.pizzeria.productos_service.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "http://localhost:4200") // <--- ¡Añadido aquí para permitir la conexión con Angular!
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping("/crear")
    public ResponseEntity<Producto> crear(@RequestBody ProductoDTO dto) {
        return new ResponseEntity<>(service.crearProducto(dto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar() {
        return new ResponseEntity<>(service.listarProductos(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        Producto producto = service.buscarPorId(id);
        if (producto != null)
            return new ResponseEntity<>(producto, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        Producto actualizado = service.actualizarProducto(id, dto);
        if (actualizado != null)
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}