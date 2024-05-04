package com.example.proyecto_2_progra_4.logic.Entities;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;


@Entity
public class Facturas {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_factura")
    private int idFactura;
    //@Basic
    //@Column(name = "id_cliente")
    //private int idCliente;
    //@Basic
    //@Column(name = "id_proveedor")
    //private int idProveedor;
    @Basic
    @Column(name = "monto")
    private Object monto;
    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", nullable = false)
    private Clientes clientesByIdCliente;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id_proveedor", nullable = false)
    private Proveedores proveedoresByIdProveedor;

    @OneToMany(mappedBy = "facturaByIdFactura")
    private Collection<Detalle_Factura> detalleFacturaByIdFactura;


    //@OneToMany(mappedBy = "facturasByIdFactura")//--
    //private Collection<Productos> productosByIdFactura;//--

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    //public int getIdCliente() {
        //return idCliente;
    //}

    //public void setIdCliente(int idCliente) {
        //this.idCliente = idCliente;
    //}

    //public int getIdProveedor() {
        //return idProveedor;
    //}

    //public void setIdProveedor(int idProveedor) {
        //this.idProveedor = idProveedor;
    //}

    public Object getMonto() {
        return monto;
    }

    public void setMonto(Object monto) {
        this.monto = monto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facturas facturas = (Facturas) o;
        return idFactura == facturas.idFactura && Objects.equals(monto, facturas.monto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFactura, monto);
    }

    public Clientes getClientesByIdCliente() {
        return clientesByIdCliente;
    }

    public void setClientesByIdCliente(Clientes clientesByIdCliente) {
        this.clientesByIdCliente = clientesByIdCliente;
    }

    public Proveedores getProveedoresByIdProveedor() {
        return proveedoresByIdProveedor;
    }

    public void setProveedoresByIdProveedor(Proveedores proveedoresByIdProveedor) {
        this.proveedoresByIdProveedor = proveedoresByIdProveedor;
    }

    public Collection<Detalle_Factura> getDetalleFacturaByIdFactura() {
        return detalleFacturaByIdFactura;
    }

    //public Collection<Productos> getProductosByIdFactura() {
        //return productosByIdFactura;
    //}

    //public void setProductosByIdFactura(Collection<Productos> productosByIdFactura) {
        //this.productosByIdFactura = productosByIdFactura;
    //}
}
