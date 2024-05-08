package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.logic.DTOEntities.ClientesDTO;
import com.example.proyecto_2_progra_4.logic.DTOEntities.ProveedoresDTO;
import com.example.proyecto_2_progra_4.logic.DTOEntities.ProductosDTO;
import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import com.example.proyecto_2_progra_4.logic.Entities.Productos;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DTOService {

    public List<ProveedoresDTO> transformarDTOProveedores(List<Proveedores> list){
        List<ProveedoresDTO> proveedpresDTO = new ArrayList<>();
        for (Proveedores current : list) {
            proveedpresDTO.add(new ProveedoresDTO(current.getIdProveedor(), current.getTipo(), current.getNombre(),
                    current.getUsuario(), current.isEstado()));
        }

        return proveedpresDTO;
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
}
