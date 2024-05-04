package com.example.proyecto_2_progra_4.presentation.Controllers;



import com.example.proyecto_2_progra_4.logic.Clientes;
import com.example.proyecto_2_progra_4.logic.Productos;
import com.example.proyecto_2_progra_4.logic.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ClienteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes/new")
    public String showSignUpForm(Model model, HttpSession session) {
        model.addAttribute("cliente", new Clientes());
        //usuario logeado actualmente
        String usuarioLogeado = (String) session.getAttribute("usuario");
        //Verificar si hay un usuario logeado
        if (usuarioLogeado != null) {
            //lista proveedor actual
            List<Clientes> listaClientes = clienteService.findClieteByProveedorActual(usuarioLogeado);
            //Agregar la lista de productos al modelo
            model.addAttribute("clientes", listaClientes);


            for (Clientes c : listaClientes) {
                //System.out.println("----------------------------\n");
                System.out.println(c.getNombre() + "\n");

            }
        }



        //model.addAttribute("clientes", clienteService.findAllClientes()); // Agrega la lista de clientes al modelo
        return "add-cliente";
    }

    @PostMapping("/clientes/add")
    public String addCliente(Clientes cliente, Model model, HttpSession session) {
        cliente.setProveedoresByIdProveedor((Proveedores) session.getAttribute("proveedor"));
        clienteService.saveCliente(cliente);
        return "redirect:/clientes/new";
    }
}
