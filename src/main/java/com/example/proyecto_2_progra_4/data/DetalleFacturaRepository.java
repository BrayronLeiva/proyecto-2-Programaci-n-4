package com.example.proyecto_2_progra_4.data;

import com.example.proyecto_2_progra_4.logic.Detalle_Factura;
import com.example.proyecto_2_progra_4.logic.Facturas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleFacturaRepository extends JpaRepository<Detalle_Factura, Long> {
}
