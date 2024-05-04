package com.example.proyecto_2_progra_4.logic;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Proveedores {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_proveedor")
    private int idProveedor;
    @Basic
    @Column(name = "tipo")
    private String tipo;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "usuario")
    private String usuario;
    @Basic
    @Column(name = "clave")
    private String clave;
    @Basic
    @Column(name = "estado")
    private boolean estado;
    @OneToMany(mappedBy = "proveedoresByIdProveedor")
    private Collection<Clientes> clientesByIdProveedor;
    @OneToMany(mappedBy = "proveedoresByIdProveedor")
    private Collection<Facturas> facturasByIdProveedor;
    @OneToMany(mappedBy = "proveedoresByIdProveedor")
    private Collection<Productos> productosByIdProveedor;

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isEstado() {return estado;}

    public void setEstado(boolean estado) {this.estado = estado;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proveedores that = (Proveedores) o;
        return idProveedor == that.idProveedor && Objects.equals(tipo, that.tipo) && Objects.equals(nombre, that.nombre) && Objects.equals(usuario, that.usuario) && Objects.equals(clave, that.clave) && estado == that.estado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProveedor, tipo, nombre, usuario, clave, estado);
    }

    public Collection<Clientes> getClientesByIdProveedor() {
        return clientesByIdProveedor;
    }

    public void setClientesByIdProveedor(Collection<Clientes> clientesByIdProveedor) {
        this.clientesByIdProveedor = clientesByIdProveedor;
    }

    public Collection<Facturas> getFacturasByIdProveedor() {
        return facturasByIdProveedor;
    }

    public void setFacturasByIdProveedor(Collection<Facturas> facturasByIdProveedor) {
        this.facturasByIdProveedor = facturasByIdProveedor;
    }

    public Collection<Productos> getProductosByIdProveedor() {
        return productosByIdProveedor;
    }

    public void setProductosByIdProveedor(Collection<Productos> productosByIdProveedor) {
        this.productosByIdProveedor = productosByIdProveedor;
    }
}
