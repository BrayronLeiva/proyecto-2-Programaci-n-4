package com.example.proyecto_2_progra_4.data;



import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedores, Long> {
    Proveedores findByUsuario(String user);
}
