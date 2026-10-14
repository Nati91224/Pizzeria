package com.pizzeria.pedidos_service.dto;

import com.pizzeria.pedidos_service.entidades.Pedido;
import lombok.Data;

@Data
public class PedidoDTO {
    private String cliente;
    private String direccionEntrega;
    private Double total;

    // Convierte el DTO a entidad Pedido
    public Pedido toEntity() {
        return new Pedido(cliente, direccionEntrega, total);
    }

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDireccionEntrega() {
		return direccionEntrega;
	}

	public void setDireccionEntrega(String direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	
}
