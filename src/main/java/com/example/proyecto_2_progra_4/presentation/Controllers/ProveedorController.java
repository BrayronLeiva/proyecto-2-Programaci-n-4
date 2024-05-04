package com.example.proyecto_2_progra_4.presentation.Controllers;



import com.example.proyecto_2_progra_4.logic.Proveedores;


import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores/new")
    public String showSignUpForm(Model model) {
        model.addAttribute("proveedor", new Proveedores());
        //model.addAttribute("proveedores", proveedorService.findAllProveedores()); // Agregar la lista de proveedores al modelo
        //List<Proveedores> proveedores = proveedorService.findAllProveedores();
        //model.addAttribute("proveedores", proveedores);

        return "add-proveedor";
    }

    /*@GetMapping("/editar/{id}")
    public String mostrarFormularioDeEdicion(@PathVariable("id") int id, Model model) {
        Proveedores proveedor = proveedorService.encontrarPorId(id);
        model.addAttribute("proveedor", proveedor);
        return "editar_proveedor";
    }*/

    @GetMapping("/editar")
    public String mostrarFormularioDeEdicion(HttpSession session, Model model) {
        System.out.println("Entre aqui");
        Proveedores p = (Proveedores) session.getAttribute("proveedor");
        Proveedores proveedor = proveedorService.encontrarPorId(p.getIdProveedor()); //para agarra el real real// puede ser inecesario
        model.addAttribute("proveedor", proveedor);
        return "editar_proveedor";
    }


    @PostMapping("/proveedores/add")
    public String addProveedor(Proveedores proveedor, Model model) {
        proveedor.setEstado(false);
        proveedorService.saveProveedor(proveedor);
        // Redirige para evitar duplicación del envío del formulario y para actualizar la lista de proveedores
        return "redirect:/proveedores/new";
    }

    @PostMapping("/proveedores/actualizar/{id}")
    public String actualizarProveedor(@PathVariable("id") long id, @ModelAttribute("proveedor") Proveedores proveedor, Model model) {
        // Opcional: Verifica que el proveedor con dicho ID exista
        Proveedores proveedorExistente = proveedorService.encontrarPorId(id);
        if (proveedorExistente != null) {
            proveedor.setIdProveedor((int) id);
            proveedor.setEstado(true);
            proveedorService.guardarProveedor(proveedor);
            return "redirect:/facturas/new"; // Asegúrate de redirigir a una ruta válida
        } else {
            // Manejo del caso en que no se encuentra el proveedor
            model.addAttribute("error", "Proveedor no encontrado");
            return "editar_proveedor";
        }
    }
}
