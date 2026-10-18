package com.pizzeria.pedidos_service.dto;

import lombok.Data;

@Data
public class ItemPedidoDTO {
    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;
}