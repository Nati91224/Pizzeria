package com.pizzeria.bff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pizzeria.bff.dto.ProductoDTO;
import com.pizzeria.bff.requester.ProductoRequester;
import java.util.List;

@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    private ProductoRequester productoRequester;

    @Override
    public ResponseEntity<ProductoDTO> crearProducto(ProductoDTO productoDTO) throws Exception {
        return productoRequester.crearProducto(productoDTO);
    }

    @Override
    public ResponseEntity<List> listarProductos() throws Exception {
        return productoRequester.listarProductos();
    }
}
