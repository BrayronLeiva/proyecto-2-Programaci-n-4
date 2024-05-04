package com.example.proyecto_2_progra_4.logic;

import jakarta.persistence.Entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "detalles_facturas")
public class Detalle_Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_detalle;

    @ManyToOne
    @JoinColumn(name = "id_factura", referencedColumnName = "id_factura")
    private Facturas facturaByIdFactura;

    @ManyToOne //ya diversas facturas pueden apunta a 1 un producto ya que solo lo apunta como referencia y no se deben de crear productos inncesarios
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")
    private Productos productoByIdProducto;
    @Basic
    @Column(name = "cantidad")
    private int cantidad;
    @Basic
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    // Constructor, getters y setters

    // Getters y setters
    public Long getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Long id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Facturas getFactura() {
        return facturaByIdFactura;
    }

    public void setFactura(Facturas factura) {
        this.facturaByIdFactura = factura;
    }

    public Productos getProducto() {
        return productoByIdProducto;
    }

    public void setProducto(Productos producto) {
        this.productoByIdProducto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
