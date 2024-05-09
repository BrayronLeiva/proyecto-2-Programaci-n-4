package com.example.proyecto_2_progra_4.logic.DTOEntities;

import com.example.proyecto_2_progra_4.logic.Entities.Facturas;
import com.example.proyecto_2_progra_4.logic.Entities.Productos;
import jakarta.persistence.*;

public class Detalle_FacturaDTO {

    private Long id_detalle;

    private int idProducto;

    private String nombreProducto;

    private int cantidad;

    private Double costo;

    public Detalle_FacturaDTO(Long id_detalle, int idProducto, String nombreProducto, int cantidad, Double costo) {
        this.id_detalle = id_detalle;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    // Constructor, getters y setters

    // Getters y setters
    public Long getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Long id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
}
