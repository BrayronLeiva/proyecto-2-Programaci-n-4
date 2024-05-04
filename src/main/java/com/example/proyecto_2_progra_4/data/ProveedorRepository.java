package com.example.proyecto_2_progra_4.data;



import com.example.proyecto_2_progra_4.logic.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedores, Long> {
    Proveedores findByUsuario(String user);
}
