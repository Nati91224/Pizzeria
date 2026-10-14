package com.pizzeria.productos_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pizzeria.productos_service.entidades.Producto;
import com.pizzeria.productos_service.dto.ProductoDTO;
import com.pizzeria.productos_service.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    public Producto crearProducto(ProductoDTO dto) {
        return repo.save(dto.toEntity());
    }

    @Override
    public List<Producto> listarProductos() {
        return repo.findAll();
    }

    @Override
    public Producto buscarPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminarProducto(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Producto actualizarProducto(Long id, ProductoDTO dto) {
        Optional<Producto> existente = repo.findById(id);
        if (existente.isPresent()) {
            Producto p = existente.get();
            p.setNombre(dto.getNombre());
            p.setDescripcion(dto.getDescripcion());
            p.setPrecio(dto.getPrecio());
            return repo.save(p);
        }
        return null;
    }
}
