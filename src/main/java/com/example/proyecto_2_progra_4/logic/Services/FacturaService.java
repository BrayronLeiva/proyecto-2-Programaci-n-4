package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.data.FacturaRepository;
import com.example.proyecto_2_progra_4.logic.Entities.Facturas;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public Facturas saveFactura(Facturas factura) {
        return facturaRepository.save(factura);
    }

    public List<Facturas> findAllFacturas() {
        return facturaRepository.findAll();
    }

    // Si necesitas buscar facturas específicas, por ejemplo, por cliente o fecha, puedes añadir esos métodos aquí.
    public Facturas findFacturaById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    // Ejemplo de método para eliminar una factura
    public void deleteFactura(Long id) {
        facturaRepository.deleteById(id);
    }

    public List<Facturas> findFacturasByProveedorActual(String usuario) {
        Proveedores proveedor = proveedorService.encontrarPorUsuario(usuario); //proveedor actual

        if (proveedor != null) {
            return facturaRepository.findByProveedoresByIdProveedor(proveedor); //productos del proveedor actual solo del proveedor actual
        } else {
            //si no encuentra proveedor actual
            return Collections.emptyList();
        }
    }
}