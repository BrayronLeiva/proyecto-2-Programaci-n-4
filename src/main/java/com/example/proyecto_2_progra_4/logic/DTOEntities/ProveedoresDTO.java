package com.example.proyecto_2_progra_4.logic.DTOEntities;

import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Facturas;
import com.example.proyecto_2_progra_4.logic.Entities.Productos;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;


public class ProveedoresDTO {

    private int idProveedor;

    private String tipo;

    private String nombre;

    private String usuario;

    private boolean estado;
    private String clave;

    public ProveedoresDTO(int idProveedor, String tipo, String nombre, String usuario, boolean estado, String clave) {
        this.idProveedor = idProveedor;
        this.tipo = tipo;
        this.nombre = nombre;
        this.usuario = usuario;
        this.estado = estado;
        this.clave = clave;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isEstado() {return estado;}

    public void setEstado(boolean estado) {this.estado = estado;}

}
