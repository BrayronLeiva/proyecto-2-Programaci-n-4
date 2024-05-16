package com.example.proyecto_2_progra_4.presentation.Controllers;


import com.example.proyecto_2_progra_4.logic.DTOEntities.Detalle_FacturaDTO;
import com.example.proyecto_2_progra_4.logic.DTOEntities.FacturasDTO;
import com.example.proyecto_2_progra_4.logic.DTOEntities.ProveedoresDTO;
import com.example.proyecto_2_progra_4.logic.Entities.*;
import com.example.proyecto_2_progra_4.logic.Services.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import java.io.ByteArrayOutputStream;


@RestController
@RequestMapping("/api")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetalleFacturaService detalleFacturaService;

    @Autowired
    private DTOService dtoService;
    private XMLService xmlService = new XMLService();
    private PDFService pdfService = new PDFService();

    List<Detalle_Factura> listaDetalleFactura = new ArrayList<>();

    @GetMapping("/facturas/new")
    public String mostrarFormularioDeRegistro(Model model, HttpSession session) {

        model.addAttribute("factura", new Facturas()); //th
        model.addAttribute("detalleFactura", new Detalle_Factura());
        model.addAttribute("clientes", clienteService.findAllClientes()); // Añadir la lista de clientes al modelo
        model.addAttribute("proveedores", proveedorService.findAllProveedores()); // Añadir la lista de proveedores al modelo
        model.addAttribute("productos", productoService.findProductosByProveedor((Proveedores) session.getAttribute("proveedor"))); //Agregar la lista de productos al modelo
        //model.addAttribute("listaItems", listaItems);
        model.addAttribute("listaDetalles", listaDetalleFactura);

        if(session.getAttribute("clienteFactura")==null) {
            Clientes cliente = new Clientes();
            cliente.setUsuario("NULL");
            session.setAttribute("clienteFactura", cliente); //tener cuidado al llamar este metodo por esta razon/ fixed

            System.out.println("SE ENTRO ACA-------------------\n\n\n");
        }
        return "registrar_factura";
    }

    @PostMapping("/facturas/add")
    public ResponseEntity<?> registrarFactura(HttpSession session, Model model) {

        try {
            Facturas factura = new Facturas();
            Clientes cliente = (Clientes) session.getAttribute("clienteFactura");
            if (listaDetalleFactura.isEmpty() || cliente == null) { //en caso de que no se pueda
                //listaDetalleFactura.clear();
                Clientes c = new Clientes();
                c.setUsuario("NULL");
                session.setAttribute("clienteFactura", c); //tener cuidado al llamar este metodo por esta razon/ fixed
            }

            factura.setProveedoresByIdProveedor((Proveedores) session.getAttribute("proveedor"));
            factura.setMonto(calcularMonto());
            factura.setClientesByIdCliente((Clientes) session.getAttribute("clienteFactura"));

            facturaService.saveFactura(factura);

            procesarListaDetalles(factura);

            Clientes c = new Clientes();
            c.setUsuario("NULL");
            session.setAttribute("clienteFactura", c); //tener cuidado al llamar este metodo por esta razon/ fixed

            return ResponseEntity.ok().build();
        }catch (Exception ex){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }

    }

    public void procesarListaDetalles(Facturas factura){
        for (int i = 0; i < listaDetalleFactura.size(); i++) {
            listaDetalleFactura.get(i).setFactura(factura);
            detalleFacturaService.saveDetalleFactura(listaDetalleFactura.get(i));
        }

        listaDetalleFactura.clear();
    }
    double calcularMonto(){
        double monto = 0;
        for(int i = 0; i < listaDetalleFactura.size(); i++){
            Double valor = (Double) listaDetalleFactura.get(i).getProducto().getValor();
            int cant = listaDetalleFactura.get(i).getCantidad();
            monto += valor*cant;
        }
        System.out.println("++++++++++++++:" + monto);
        return monto;
    }


    @PostMapping("/facturas/addItem/{productoId}/{cantidad}")
    public ResponseEntity<?> registrarDetalleFactura(@PathVariable Long productoId, @PathVariable int cantidad) {
        try {
            Productos producto = productoService.findById(Math.toIntExact(productoId));
            if (producto.getNombre() != null) {
                System.out.println("--------------: " + producto.getNombre());
                System.out.println("--------------:" + cantidad);
                Detalle_Factura detalleFactura = new Detalle_Factura();
                detalleFactura.setCantidad(cantidad);
                detalleFactura.setProducto(producto);
                detalleFactura.setPrecioUnitario((Double.parseDouble(String.valueOf(producto.getValor()))));
                //listaItems.add(producto);
                listaDetalleFactura.add(detalleFactura);
                return ResponseEntity.ok().build();
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Codigo de producto no valido");

    }

    @GetMapping("/facturas/getCarrito")
    public ResponseEntity<List<Detalle_FacturaDTO>> getCarrito() {
        System.out.println("Estoy mandando el carrito");
        try {
            List<Detalle_FacturaDTO> carrito = dtoService.transformarDTOdetallesFactura(listaDetalleFactura); // Suponiendo que tienes un método en tu servicio para obtener todos los proveedores
            System.out.println(carrito.size());
            return ResponseEntity.ok().body(carrito);
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PostMapping("/facturas/aumentarCantidad/{index}")
    public ResponseEntity<?> aumentarCantidad(@PathVariable int index) {
        try {
            // Asumiendo que 'listaDetalleFactura' está guardada en la sesión o es un campo en el controlador
            Detalle_Factura detalle = listaDetalleFactura.get(index);
            detalle.setCantidad(detalle.getCantidad() + 1);
            System.out.println("Cantidad de item " + detalle.getProducto().getNombre() + " = " + detalle.getCantidad());
            System.out.println("Valor de item " + detalle.getProducto().getNombre() + " = " + detalle.getPrecioUnitario());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/facturas/disminuirCantidad/{index}")
    public ResponseEntity<?> disminuirCantidad(@PathVariable int index) {
        try {
            // Asumiendo que 'listaDetalleFactura' está guardada en la sesión o es un campo en el controlador
            Detalle_Factura detalle = listaDetalleFactura.get(index);
            detalle.setCantidad(detalle.getCantidad() - 1);
            if (detalle.getCantidad()==0){
                listaDetalleFactura.remove(index);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/facturas/getFacturas")
    public ResponseEntity<?> listarFacturas(Model model, HttpSession session) {
        try {
            //usuario logeado actualmente
            String usuarioLogeado = (String) session.getAttribute("usuario");
            //Verificar si hay un usuario logeado
            if (usuarioLogeado != null) {
                //lista proveedor actual
                List<Facturas> listaFacturas = facturaService.findFacturasByProveedorActual(usuarioLogeado);
                return ResponseEntity.ok().body(dtoService.transformarDTOFacturas(listaFacturas));
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Usuario No Esta Logeado.");

    }

    @PostMapping("/facturas/selectCliente/{id}")
    public ResponseEntity<?> seleccionarCliente(@PathVariable("id") long id, HttpSession session, Model model) {
        System.out.println("Cayendo a selectCliente");
        try {
            Clientes cliente = clienteService.findClienteById(Integer.parseInt(String.valueOf(id)));
            if (cliente != null) {
                System.out.println("ASIGNANDO "+ cliente.getNombre());
                session.setAttribute("clienteFactura", cliente);
            } else {
                System.out.println("No se encontro\n");
                return ResponseEntity.ok().body(Collections.singletonMap("nombreCliente", "No hay ningun cliente seleccionado"));
                //Clientes c = new Clientes();
                //c.setUsuario("NULL");
                //session.setAttribute("clienteFactura", c); //tener cuidado al llamar este metodo por esta razon/ fixed
            }
            return ResponseEntity.ok().body(Collections.singletonMap("nombreCliente",  cliente.getNombre()));

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
        //model.addAttribute("listaItems", listaItems);
        //model.addAttribute("listaDetalles", listaDetalleFactura);

    }

    @GetMapping("facturas/getNombreClienteFactura")
    public ResponseEntity<?> getNombreClienteFactura(HttpSession session){
        try {
            Clientes clienteFactura = (Clientes) session.getAttribute("clienteFactura");
            if (clienteFactura==null){
                return ResponseEntity.ok().body(Collections.singletonMap("nombreCliente", "No hay ningun cliente seleccionado"));
            }else {
                return ResponseEntity.ok().body(Collections.singletonMap("nombreCliente",  clienteFactura.getNombre()));
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error. Por favor, inténtalo de nuevo más tarde.");
        }
    }

    @PostMapping("/buscarCliente")
    public String buscarCliente(@RequestParam("clienteID") int clienteID, HttpSession session, Model model) {
        Clientes cliente = clienteService.findClienteById(clienteID);
        if(cliente!=null) {
            session.setAttribute("clienteFactura", cliente);
        }else{
            Clientes c = new Clientes();
            c.setUsuario("NULL");
            session.setAttribute("clienteFactura", c); //tener cuidado al llamar este metodo por esta razon/ fixed
        }

        //model.addAttribute("listaItems", listaItems);
        model.addAttribute("listaDetalles", listaDetalleFactura);

        return "registrar_factura"; //dudoso
    }

    @PostMapping("/facturas/selectProducto/{id}")
    public ResponseEntity<?> seleccionarProducto(@PathVariable("id") long id) {
        try {
            Productos producto = productoService.findById((int) id);
            if (producto != null) {
                Detalle_Factura detalleFactura = new Detalle_Factura();
                detalleFactura.setCantidad(1);
                detalleFactura.setProducto(producto);
                detalleFactura.setPrecioUnitario(Double.parseDouble(String.valueOf(producto.getValor())));
                listaDetalleFactura.add(detalleFactura);
                System.out.println("SI LO ENCONTRO\n\n\n\n\n\n\n");
            } else {
                throw new Exception("No se encontro el producto\n");
            }
            return ResponseEntity.ok().build();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ex.getMessage());
        }
    }


    @PostMapping("/buscarProducto")
    public String buscarProducto(@RequestParam("productoID") int productoId){
        Productos producto = productoService.findById(productoId);
        if(producto!=null){
            Detalle_Factura detalleFactura = new Detalle_Factura();
            detalleFactura.setCantidad(1);
            detalleFactura.setProducto(producto);
            detalleFactura.setPrecioUnitario(Double.parseDouble(String.valueOf(producto.getValor())));
            //listaItems.add(producto);
            listaDetalleFactura.add(detalleFactura);
            System.out.println("SI LO ENCONTRO\n\n\n\n\n\n\n");
        }else System.out.println("NO LO ENCONTRO\n\n\n\n\n\n\n");
        return "redirect:/facturas/new";
    }

    @GetMapping("/factura/{id}/descargarPDF")
    public void descargarPdf(@PathVariable Long id, HttpServletResponse response) {
        try {
            Facturas factura = facturaService.findFacturaById(id); // Línea completada aquí
            ByteArrayOutputStream baos = pdfService.generarPdf(factura);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=factura-" + id + ".pdf");
            response.getOutputStream().write(baos.toByteArray());
        } catch (IOException e) {
            System.err.println("Error al enviar PDF: " + e.getMessage());
        }
    }

    @GetMapping("/factura/{id}/descargarXML")
    public void generarXml(@PathVariable Long id, HttpServletResponse response) throws IOException {
        // Generar el documento XML
        Facturas factura = facturaService.findFacturaById(id);
        org.jdom2.Document xmlDocument = xmlService.generarDocumentoXML(factura);

        // Establecer el tipo de contenido de la respuesta
        response.setContentType("application/xml");
        response.setCharacterEncoding("UTF-8");

        // Establecer el encabezado Content-Disposition para indicar que el archivo debe descargarse
        response.setHeader("Content-Disposition", "attachment; filename=facturas.xml");

        // Obtener el flujo de salida de la respuesta
        try (OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(), "UTF-8")) {
            // Escribir el documento XML en el flujo de salida
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(xmlDocument, out);
        }
    }




}
