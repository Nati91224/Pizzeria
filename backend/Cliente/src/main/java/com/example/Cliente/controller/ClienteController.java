package com.example.Cliente.controller;

import com.example.Cliente.DTO.ClienteDto;
import com.example.Cliente.entity.Cliente;
import com.example.Cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping("/crear")
    public ResponseEntity<Cliente> crear(@RequestBody ClienteDto dto) {
        return new ResponseEntity<>(service.crearCliente(dto), HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listar() {
        return new ResponseEntity<>(service.listarClientes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long id) {
        Cliente cliente = service.buscarPorId(id);
        return (cliente != null)
                ? new ResponseEntity<>(cliente, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody ClienteDto dto) {
        Cliente actualizado = service.actualizarCliente(id, dto);
        return (actualizado != null)
                ? new ResponseEntity<>(actualizado, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}