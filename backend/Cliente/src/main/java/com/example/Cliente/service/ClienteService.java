package com.example.Cliente.service;

import com.example.Cliente.DTO.ClienteDto;
import com.example.Cliente.entity.Cliente;
import com.example.Cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente crearCliente(ClienteDto dto) {
        return repository.save(dto.toEntity());
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Cliente actualizarCliente(Long id, ClienteDto dto) {
        Cliente existente = repository.findById(id).orElse(null);
        if (existente == null) return null;
        existente.setNombre(dto.getNombre());
        existente.setTelefono(dto.getTelefono());
        existente.setDireccion(dto.getDireccion());
        existente.setEmail(dto.getEmail());
        return repository.save(existente);
    }

    public void eliminarCliente(Long id) {
        repository.deleteById(id);
    }
}