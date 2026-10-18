package com.pizzeria.pedidos_service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoDTO {
    private String cliente;
    private String telefono;
    private String direccionEntrega;
    private String notas;
    private Double total;
    private List<ItemPedidoDTO> items = new ArrayList<>();
}