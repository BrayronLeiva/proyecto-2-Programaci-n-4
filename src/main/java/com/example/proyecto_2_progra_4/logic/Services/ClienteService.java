package com.example.proyecto_2_progra_4.logic.Services;



import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.data.ClienteRepository;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Clientes saveCliente(Clientes cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Clientes> findAllClientes() {
        return clienteRepository.findAll();
    }
    public List<Clientes> findClieteByProveedorActual(String usuario) {
        Proveedores proveedor = proveedorService.encontrarPorUsuario(usuario); //proveedor actual

        if (proveedor != null) {
            return clienteRepository.findByProveedoresByIdProveedor(proveedor); //productos del proveedor actual solo del proveedor actual
        } else {
            //si no encuentra proveedor actual
            return Collections.emptyList();
        }
    }

    public Clientes findClienteById(int id){
        return clienteRepository.findByIdCliente(id);
    }

}
