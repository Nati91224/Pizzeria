package com.example.Cliente.DTO;

import com.example.Cliente.entity.Cliente;

public class ClienteDto {

    private String nombre;
    private String telefono;
    private String direccion;
    private String email;

    public Cliente toEntity() {
        Cliente c = new Cliente();
        c.setNombre(nombre);
        c.setTelefono(telefono);
        c.setDireccion(direccion);
        c.setEmail(email);
        return c;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}