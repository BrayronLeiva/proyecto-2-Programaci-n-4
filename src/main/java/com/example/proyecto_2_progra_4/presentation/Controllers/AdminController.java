package com.example.proyecto_2_progra_4.presentation.Controllers;

import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private ProveedorService proveedorService;

    @PostMapping("/admin/activarProveedor/{id}")
    public ResponseEntity<?> activarProveedor(@PathVariable String id) {
        System.out.println("Estoy callendo a activar un proveedor con el id" + id);
        Proveedores p = proveedorService.encontrarPorId(Integer.parseInt(id));
        p.setEstado(true);
        proveedorService.saveProveedor(p); //para que uptade
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admin/desactivarProveedor/{id}")
    public ResponseEntity<?> desactivarProveedor(@PathVariable String id) {
        System.out.println("Estoy callendo a desactivar un proveedor con el id" + id);
        Proveedores p = proveedorService.encontrarPorId(Integer.parseInt(id));
        p.setEstado(false);
        proveedorService.saveProveedor(p); //para que uptade
        return ResponseEntity.ok().build();
    }


}
