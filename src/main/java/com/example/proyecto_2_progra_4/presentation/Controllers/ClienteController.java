package com.example.proyecto_2_progra_4.presentation.Controllers;



import com.example.proyecto_2_progra_4.logic.DTOEntities.ClientesDTO;
import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ClienteService;
import com.example.proyecto_2_progra_4.logic.Services.DTOService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private DTOService dtoService;

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
    public ResponseEntity<?> addCliente(@RequestBody Clientes cliente, HttpSession session) {

        System.out.println("CALLENDO EN EL AGREGAR CLIENTE");
        try {
            cliente.setProveedoresByIdProveedor((Proveedores) session.getAttribute("proveedor"));
            clienteService.saveCliente(cliente);
            return ResponseEntity.ok().build();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
    }

    @GetMapping("/clientes/getClientes")
    public ResponseEntity<List<ClientesDTO>> getClientes(HttpSession session) {
        try {
            Proveedores pActual= (Proveedores) session.getAttribute("proveedor");
            String pUser = pActual.getUsuario();
            List<Clientes> clientes = clienteService.findClieteByProveedorActual(pUser); // Suponiendo que tienes un método en tu servicio para obtener todos los proveedores
            System.out.println("SON: " + clientes.size());
            return ResponseEntity.ok().body(dtoService.transformarDTOClientes(clientes));
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
