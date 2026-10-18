package com.pizzeria.bff.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PedidoDTO {
    private Long id;
    private String cliente;
    private String telefono;
    private String direccionEntrega;
    private String notas;
    private Double total;
    private String estado;
    private List<ItemPedidoDTO> items = new ArrayList<>();
}