package com.example.proyecto_2_progra_4.presentation.Controllers;



import com.example.proyecto_2_progra_4.logic.DTOEntities.ProveedoresDTO;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;


import com.example.proyecto_2_progra_4.logic.Services.DTOService;
import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/proveedores/new")
    public String showSignUpForm(Model model) {
        model.addAttribute("proveedor", new Proveedores());
        //model.addAttribute("proveedores", proveedorService.findAllProveedores()); // Agregar la lista de proveedores al modelo
        //List<Proveedores> proveedores = proveedorService.findAllProveedores();
        //model.addAttribute("proveedores", proveedores);

        return "add-proveedor";
    }

    @GetMapping("/proveedores/getProveedores")
    public ResponseEntity<List<ProveedoresDTO>> getProveedores() {
        try {
            List<Proveedores> proveedores = proveedorService.findAllProveedores(); // Suponiendo que tienes un método en tu servicio para obtener todos los proveedores
            System.out.println(proveedores.size());
            return ResponseEntity.ok().body(dtoService.transformarDTOProveedores(proveedores));
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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

    @PostMapping("/proveedor/add")
    public ResponseEntity<?> addProveedor(@RequestBody Proveedores proveedor) {

        System.out.println(proveedor.toString());

        System.out.println("CALLENDO EN EL AGREGAR PROVEEDOR");
        try {
            proveedor.setEstado(false);
            proveedorService.saveProveedor(proveedor);
            // Redirige para evitar duplicación del envío del formulario y para actualizar la lista de proveedores
            return ResponseEntity.ok().build();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
    }


    @PostMapping("/proveedores/add2")
    public String addProveedor2(Proveedores proveedor, Model model) {
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


    @PostMapping("/proveedores/update")
    public ResponseEntity<?> updateProveedor(@RequestBody Proveedores proveedor, HttpSession session) {
        try {
            Proveedores proveedorActual = (Proveedores) session.getAttribute("proveedor");
            if (proveedorActual == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay proveedor en sesión.");
            }
            proveedorActual.setNombre(proveedor.getNombre());
            proveedorActual.setTipo(proveedor.getTipo());
            proveedorActual.setUsuario(proveedor.getUsuario());
            proveedorActual.setClave(proveedor.getClave());
            proveedorService.saveProveedor(proveedorActual);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar los datos del proveedor.");
        }
    }
}
