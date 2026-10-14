package com.pizzeria.productos_service.service;

import java.util.List;
import com.pizzeria.productos_service.dto.ProductoDTO;
import com.pizzeria.productos_service.entidades.Producto;

public interface ProductoService {
    Producto crearProducto(ProductoDTO dto);
    List<Producto> listarProductos();
    Producto buscarPorId(Long id);
    void eliminarProducto(Long id);
    Producto actualizarProducto(Long id, ProductoDTO dto);
}
