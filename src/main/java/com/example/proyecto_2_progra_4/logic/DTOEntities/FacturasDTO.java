package com.example.proyecto_2_progra_4.logic.DTOEntities;

import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Detalle_Factura;
import com.example.proyecto_2_progra_4.logic.Entities.Facturas;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

public class FacturasDTO {

    private int idFactura;

    private Double monto;

    private String nombreCliente;

    public FacturasDTO(int idFactura, Double monto, String nombreCliente) {
        this.idFactura = idFactura;
        this.monto = monto;
        this.nombreCliente = nombreCliente;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
}
