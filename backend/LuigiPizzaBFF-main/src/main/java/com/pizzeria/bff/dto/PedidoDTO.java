package com.pizzeria.bff.dto;

import lombok.Data;

@Data
public class PedidoDTO {
    private Long id;
    private String cliente;
    private String direccionEntrega;
    private Double total;
    private String estado;
}
