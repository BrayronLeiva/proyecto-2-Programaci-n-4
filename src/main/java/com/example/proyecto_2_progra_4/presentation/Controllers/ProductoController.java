package com.example.proyecto_2_progra_4.presentation.Controllers;



import com.example.proyecto_2_progra_4.logic.DTOEntities.ProductosDTO;
import com.example.proyecto_2_progra_4.logic.DTOEntities.ProveedoresDTO;
import com.example.proyecto_2_progra_4.logic.Entities.Productos;

import com.example.proyecto_2_progra_4.logic.Entities.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ClienteService;
import com.example.proyecto_2_progra_4.logic.Services.DTOService;
import com.example.proyecto_2_progra_4.logic.Services.ProductoService;
import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private DTOService dtoService;

    @GetMapping("/productos/new")
    public String showSignUpForm(Model model, HttpSession session) {
        model.addAttribute("producto", new Productos());
        model.addAttribute("proveedores", proveedorService.findAllProveedores()); // Agrega la lista de proveedores al modelo // Lista de productos
        model.addAttribute("clientes", clienteService.findAllClientes()); // Agrega la lista de clientes al modelo
        //List<Productos> listaProductos = productoService.findAllProductos();
        //usuario logeado actualmente
        String usuarioLogeado = (String) session.getAttribute("usuario");
        //Verificar si hay un usuario logeado
        if (usuarioLogeado != null) {
            //lista proveedor actual
            List<Productos> listaProductos = productoService.findProductosByProveedorActual(usuarioLogeado);
            //Agregar la lista de productos al modelo
            model.addAttribute("productos", listaProductos);
        }
        return "add-producto";
    }

    @PostMapping("/productos/add")
    public ResponseEntity<?> addProducto(@RequestBody Productos producto, HttpSession session) {
        try {
            producto.setProveedoresByIdProveedor((Proveedores) session.getAttribute("proveedor"));
            productoService.saveProducto(producto);
            return ResponseEntity.ok().build();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
    }

    @GetMapping("/productos/getProductos")
    public ResponseEntity<List<ProductosDTO>> getProveedores(HttpSession session) {
        try {
            String usuarioLogeado = (String) session.getAttribute("usuario");
            //Verificar si hay un usuario logeado
            List<Productos> listaProductos = productoService.findProductosByProveedorActual(usuarioLogeado);


            return ResponseEntity.ok().body(dtoService.transformarDTOProductos(listaProductos));
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
