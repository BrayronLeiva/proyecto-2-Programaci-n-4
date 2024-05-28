package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.logic.DTOEntities.*;
import com.example.proyecto_2_progra_4.logic.Entities.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOService {

    public List<ProveedoresDTO> transformarDTOProveedores(List<Proveedores> list){
        List<ProveedoresDTO> proveedpresDTO = new ArrayList<>();
        for (Proveedores current : list) {
            proveedpresDTO.add(new ProveedoresDTO(current.getIdProveedor(), current.getTipo(), current.getNombre(),
                    current.getUsuario(), current.isEstado(),current.getClave()));
        }

        return proveedpresDTO;
    }

    public ProveedoresDTO transformarDTOProveedore(Proveedores obj){

        return new ProveedoresDTO(obj.getIdProveedor(), obj.getTipo(), obj.getNombre(),
                    obj.getUsuario(), obj.isEstado(), obj.getClave());

    }

    public List<ClientesDTO> transformarDTOClientes(List<Clientes> list){
        List<ClientesDTO> clientesDTO = new ArrayList<>();
        for (Clientes current : list) {
            clientesDTO.add(new ClientesDTO(current.getIdCliente(), current.getNombre()));
        }

        return clientesDTO;
    }

    public List<ProductosDTO> transformarDTOProductos(List<Productos> list){
        List<ProductosDTO> productosDTO = new ArrayList<>();
        for (Productos current : list) {
            productosDTO.add(new ProductosDTO(current.getIdProducto(), current.getNombre(), current.getValor()));
        }

        return productosDTO;
    }

    public List<Detalle_FacturaDTO> transformarDTOdetallesFactura(List<Detalle_Factura> list){
        List<Detalle_FacturaDTO> detallesDTO = new ArrayList<>();
        for (Detalle_Factura current : list) {
            detallesDTO.add(new Detalle_FacturaDTO(current.getId_detalle(), current.getProducto().getIdProducto(), current.getProducto().getNombre(),
                    current.getCantidad(), current.getPrecioUnitario()*current.getCantidad()));
        }

        return detallesDTO;
    }

    public List<FacturasDTO> transformarDTOFacturas(List<Facturas> list){
        List<FacturasDTO> facturasDTO = new ArrayList<>();
        for (Facturas current : list) {
            facturasDTO.add(new FacturasDTO(current.getIdFactura(),(Double) current.getMonto(), current.getClientesByIdCliente().getNombre()));
        }

        return facturasDTO;
    }

    public FacturasDTO transformarDTOFactura(Facturas obj){

        return new FacturasDTO(obj.getIdFactura(),(Double) obj.getMonto(), obj.getClientesByIdCliente().getNombre(),
                obj.getClientesByIdCliente().getIdCliente(), obj.getProveedoresByIdProveedor().getIdProveedor());
    }




}
