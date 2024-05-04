package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.data.DetalleFacturaRepository;
import com.example.proyecto_2_progra_4.logic.Detalle_Factura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetalleFacturaService {
    @Autowired
    private DetalleFacturaRepository detalleFacturaRepository;

    //@Autowired
    //public DetalleFacturaService(DetalleFacturaRepository facturaRepository) {
      //  this.detalleFacturaRepository = facturaRepository;
    //}

    public Detalle_Factura saveDetalleFactura(Detalle_Factura detalleFactura) {
        return detalleFacturaRepository.save(detalleFactura);
    }

    public List<Detalle_Factura> findAllDetalleFacturas() {
        return detalleFacturaRepository.findAll();
    }

    // Si necesitas buscar facturas específicas, por ejemplo, por cliente o fecha, puedes añadir esos métodos aquí.
    public Detalle_Factura findDetalleFacturaById(Long id) {
        return detalleFacturaRepository.findById(id).orElse(null);
    }

    // Ejemplo de método para eliminar una factura
    public void deleteDetalleFactura(Long id) {
        detalleFacturaRepository.deleteById(id);
    }

    // Puedes añadir más lógica específica del negocio relacionada con facturas si es necesario
}