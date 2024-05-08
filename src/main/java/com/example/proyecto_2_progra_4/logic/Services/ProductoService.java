package com.example.proyecto_2_progra_4.logic.Services;


import com.example.proyecto_2_progra_4.logic.Entities.Productos;
import com.example.proyecto_2_progra_4.data.ProductoRepository;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Productos saveProducto(Productos producto) {
        return productoRepository.save(producto);
    }

    public List<Productos> findAllProductos() {
        return productoRepository.findAll();
    }
    public List<Productos> findProductosByProveedor(Proveedores proveedor) {
        return productoRepository.findByProveedoresByIdProveedor(proveedor);
    }

    public List<Productos> findProductosByProveedorActual(String usuario) {
        Proveedores proveedor = proveedorService.encontrarPorUsuario(usuario); //proveedor actual

        if (proveedor != null) {
            return productoRepository.findByProveedoresByIdProveedor(proveedor); //productos del proveedor actual solo del proveedor actual
        } else {
            //si no encuentra proveedor actual
            return Collections.emptyList();
        }
    }
    public Productos findById(int id) {
        return productoRepository.findByIdProducto(id);
    }

}
///////
