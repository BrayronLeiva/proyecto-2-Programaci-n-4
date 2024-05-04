package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.logic.Proveedores;
import com.example.proyecto_2_progra_4.data.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {
    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public Proveedores saveProveedor(Proveedores proveedor) {
        return proveedorRepository.save(proveedor);
    }

    // Método para obtener todos los proveedores
    public List<Proveedores> findAllProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedores encontrarPorId(long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

   public void guardarProveedor(Proveedores proveedor) {
        proveedorRepository.save(proveedor);
    }

    public boolean validarCredenciales(String user, String clave) {
        Proveedores proveedor = proveedorRepository.findByUsuario(user);
        return proveedor != null && proveedor.getClave().equals(clave);
    }
    public Proveedores encontrarPorUsuario(String user){
        return proveedorRepository.findByUsuario(user);
    }
}
