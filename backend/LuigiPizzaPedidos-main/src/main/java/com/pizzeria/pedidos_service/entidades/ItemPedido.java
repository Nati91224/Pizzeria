package com.pizzeria.pedidos_service.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private Double precioUnitario;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}