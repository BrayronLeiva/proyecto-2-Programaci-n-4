package com.example.proyecto_2_progra_4.data;

import com.example.proyecto_2_progra_4.logic.Entities.Detalle_Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleFacturaRepository extends JpaRepository<Detalle_Factura, Long> {
}
