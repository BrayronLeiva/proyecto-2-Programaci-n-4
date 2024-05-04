package com.example.proyecto_2_progra_4.data;


import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Clientes, Long> {
    List<Clientes> findByProveedoresByIdProveedor(Proveedores proveedor);
    Clientes findByIdCliente(int id);
}
