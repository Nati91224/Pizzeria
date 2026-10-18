package com.pizzeria.bff.requester;

import java.util.List;
import org.springframework.http.ResponseEntity;

import com.pizzeria.bff.dto.PedidoDTO;
import com.pizzeria.bff.dto.ProductoDTO;

public interface ProductoRequester {
    ResponseEntity<ProductoDTO> crearProducto(ProductoDTO productoDTO) throws Exception;
    ResponseEntity<List> listarProductos() throws Exception;
    
}
