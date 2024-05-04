package com.example.proyecto_2_progra_4.data;



import com.example.proyecto_2_progra_4.logic.Entities.Productos;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Productos, Long> {
    List<Productos> findByProveedoresByIdProveedor(Proveedores proveedor);
    Productos findByIdProducto(int id);
}
