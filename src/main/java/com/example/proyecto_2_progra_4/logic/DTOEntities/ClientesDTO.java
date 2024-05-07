package com.example.proyecto_2_progra_4.logic.DTOEntities;

import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Facturas;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

public class ClientesDTO {
    public ClientesDTO(int idCliente, String nombre) {
        this.idCliente = idCliente;
        this.nombre = nombre;
    }
    private int idCliente;
    private String nombre;
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
