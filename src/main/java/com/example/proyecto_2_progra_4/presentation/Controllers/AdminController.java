package com.example.proyecto_2_progra_4.presentation.Controllers;

import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("proveedores", proveedorService.findAllProveedores());

        return "admin";

    }
    @PostMapping("/activarProveedor")
    public String activarProveedor(@RequestParam("idProveedor") int id, Model model, HttpSession session) {

        Proveedores p = proveedorService.encontrarPorId(id);
        p.setEstado(true);
        proveedorService.saveProveedor(p); //para que uptade
        return "redirect:/admin";
    }

    @PostMapping("/desactivarProveedor")
    public String desactivarProveedor(@RequestParam("idProveedor") int id, Model model, HttpSession session) {

        Proveedores p = proveedorService.encontrarPorId(id);
        p.setEstado(false);
        proveedorService.saveProveedor(p); //para que uptade
        return "redirect:/admin";
    }

}
