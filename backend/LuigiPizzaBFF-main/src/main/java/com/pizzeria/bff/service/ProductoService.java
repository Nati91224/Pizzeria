package com.pizzeria.bff.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.pizzeria.bff.dto.ProductoDTO;

public interface ProductoService {
    ResponseEntity<ProductoDTO> crearProducto(ProductoDTO productoDTO) throws Exception;
    ResponseEntity<List> listarProductos() throws Exception;
}
