package com.pizzeria.pedidos_service.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private String telefono;
    private String direccionEntrega;
    @Column(length = 500)
    private String notas;
    private Double total;
    private String estado = "SOLICITADO";

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemPedido> items = new ArrayList<>();

    public Pedido() {}

    public Pedido(String cliente, String direccionEntrega, Double total) {
        this.cliente = cliente;
        this.direccionEntrega = direccionEntrega;
        this.total = total;
        this.estado = "SOLICITADO";
    }
}