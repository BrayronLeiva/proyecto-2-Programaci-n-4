package com.example.proyecto_2_progra_4.presentation.Controllers;

import com.example.proyecto_2_progra_4.logic.DTOEntities.ProveedoresDTO;
import com.example.proyecto_2_progra_4.logic.Entities.Clientes;
import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ClienteService;
import com.example.proyecto_2_progra_4.logic.Services.DTOService;
import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Objects;


@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private DTOService dtoService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Proveedores usuario, HttpSession session) {
        System.out.println("estoy callendo en el rest controller");
        try {
            String username = usuario.getUsuario();
            String password = usuario.getClave();

            Proveedores proveedor = proveedorService.encontrarPorUsuario(username);

            System.out.println(username);
            System.out.println(password);


            if(Objects.equals(username, "admin") && Objects.equals(password, "admin")){
                //enviar a section de administrador
                return ResponseEntity.ok().body(Collections.singletonMap("admin", true));
            }

            if(proveedorService.validarCredenciales(username, password) && proveedor.isEstado()){
                // Obtener el proveedor autenticado
                // Almacenar el ID del proveedor en la sesión
                session.setAttribute("proveedor", proveedor);
                session.setAttribute("usuario", proveedor.getUsuario());


                return ResponseEntity.ok().body(Collections.singletonMap("admin", false));

            } else {
                // Credenciales incorrectas o proveedor inactivo
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) { //agarra todas las excepciones como un error de inicio de sesion
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            //cae aca debido a que no encuentra la vara
        }
    }

    @GetMapping("/logOut")
    public ResponseEntity<?> login(HttpSession session) {
        try {
            if (session != null) {
                session.invalidate();
            }
            return ResponseEntity.ok().build();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }

    @GetMapping("/getUser")
    private ResponseEntity<ProveedoresDTO> getUser(HttpSession session){
        try{
            ProveedoresDTO user = dtoService.transformarDTOProveedore((Proveedores) session.getAttribute("proveedor"));
            return ResponseEntity.ok().body(user);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
