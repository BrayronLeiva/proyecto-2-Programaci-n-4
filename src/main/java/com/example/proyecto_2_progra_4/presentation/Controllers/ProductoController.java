package com.example.proyecto_2_progra_4.presentation.Controllers;



import com.example.proyecto_2_progra_4.logic.Clientes;
import com.example.proyecto_2_progra_4.logic.Productos;

import com.example.proyecto_2_progra_4.logic.Proveedores;
import com.example.proyecto_2_progra_4.logic.Services.ClienteService;
import com.example.proyecto_2_progra_4.logic.Services.ProductoService;
import com.example.proyecto_2_progra_4.logic.Services.ProveedorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProveedorService proveedorService;
    @Autowired
    private ClienteService clienteService;

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
    public String addProducto(Productos producto, Model model, HttpSession session) {

        producto.setProveedoresByIdProveedor((Proveedores) session.getAttribute("proveedor"));
        productoService.saveProducto(producto);
        return "redirect:/productos/new";
    }
}
