package com.pizzeria.productos_service.dto;

import com.pizzeria.productos_service.entidades.Producto;
import lombok.Data;

@Data
public class ProductoDTO {
    private String nombre;
    private String descripcion;
    private Double precio;

    public Producto toEntity() {
        return new Producto(null, nombre, descripcion, precio);
    }
}
