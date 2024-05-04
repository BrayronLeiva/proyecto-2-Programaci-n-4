package com.example.proyecto_2_progra_4.presentation.Controllers;


import com.example.proyecto_2_progra_4.logic.Entities.*;
import com.example.proyecto_2_progra_4.logic.Services.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


import java.io.ByteArrayOutputStream;


@Controller
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

    private XMLService xmlService = new XMLService();
    private PDFService pdfService = new PDFService();


    //List<Productos> listaItems = new ArrayList<>(); //lista de los productos que se van a comprar
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
    public String registrarFactura(Facturas factura,HttpSession session, Model model) {
        Clientes cliente = (Clientes) session.getAttribute("clienteFactura");
        if(listaDetalleFactura.isEmpty() || cliente==null){ //en caso de que no se pueda
            //listaDetalleFactura.clear();
            Clientes c = new Clientes();
            c.setUsuario("NULL");
            session.setAttribute("clienteFactura", c); //tener cuidado al llamar este metodo por esta razon/ fixed
            return "redirect:/facturas/new";
        }

        factura.setProveedoresByIdProveedor((Proveedores) session.getAttribute("proveedor"));
        factura.setMonto(calcularMonto());
        factura.setClientesByIdCliente((Clientes) session.getAttribute("clienteFactura"));

        facturaService.saveFactura(factura);

        for(int i = 0; i < listaDetalleFactura.size(); i++){
            listaDetalleFactura.get(i).setFactura(factura);
            detalleFacturaService.saveDetalleFactura(listaDetalleFactura.get(i));
        }
        //listaItems.clear();
        listaDetalleFactura.clear();

        Clientes c = new Clientes();
        c.setUsuario("NULL");
        session.setAttribute("clienteFactura", c); //tener cuidado al llamar este metodo por esta razon/ fixed

        return "redirect:/facturas/new";
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


    @PostMapping("/facturas/add_Item")
    public String registrarDetalleFactura(@ModelAttribute("productoByIdProducto") Productos producto, @RequestParam("cantidad") int cantidad, Model model) {
        if(producto.getNombre()!=null) {
            System.out.println("--------------: " + producto.getNombre());
            System.out.println("--------------:" + cantidad);
            Detalle_Factura detalleFactura = new Detalle_Factura();
            detalleFactura.setCantidad(cantidad);
            detalleFactura.setProducto(producto);
            detalleFactura.setPrecioUnitario(10.0);
            //listaItems.add(producto);
            listaDetalleFactura.add(detalleFactura);
        }

        //listaDetalleFactura.add();
        //detalleFactura.setFactura(n);


        //facturaService.saveFactura(factura);
        return "redirect:/facturas/new";
    }

    /*@PostMapping("/aumentarCantidad")
    public String aumentarProductoDetalle(Long detalleFactura,HttpSession session, Model model) {

        for (Detalle_Factura indiceObj : listaDetalleFactura) {
            if (Objects.equals(indiceObj.getId_detalle(), detalleFactura)) {
                indiceObj.setCantidad(indiceObj.getCantidad() + 1);
                break;
            }
        }
        return "redirect:/facturas/new";
    }*/

    @PostMapping("/aumentarCantidad")
    public String aumentarCantidad(@RequestParam("index") int index, HttpSession session) {
        // Asumiendo que 'listaDetalleFactura' está guardada en la sesión o es un campo en el controlador
        Detalle_Factura detalle = listaDetalleFactura.get(index);
        detalle.setCantidad(detalle.getCantidad() + 1);
        // Asegúrate de actualizar la sesión o la lista según sea necesario
        return "redirect:/facturas/new";
    }

   /* @PostMapping("/disminuirCantidad")
    public String disminuirProductoDetalle(Long detalleFactura,HttpSession session, Model model) {

        for (Detalle_Factura indiceObj : listaDetalleFactura) {
            if (Objects.equals(indiceObj.getId_detalle(), detalleFactura)) {
                indiceObj.setCantidad(indiceObj.getCantidad() - 1);
                break;
            }
        }
        return "redirect:/facturas/new";
    }*/


    @PostMapping("/disminuirCantidad")
    public String disminuirCantidad(@RequestParam("index") int index, HttpSession session) {
        Detalle_Factura detalle = listaDetalleFactura.get(index);
        detalle.setCantidad(detalle.getCantidad() - 1);
        if (detalle.getCantidad()==0){
            listaDetalleFactura.remove(index);
        }
        // Asegúrate de actualizar la sesión o la lista según sea necesario
        return "redirect:/facturas/new";
    }

    @GetMapping("/facturas")
    public String listarFacturas(Model model, HttpSession session) {

        //usuario logeado actualmente
        String usuarioLogeado = (String) session.getAttribute("usuario");
        //Verificar si hay un usuario logeado
        if (usuarioLogeado != null) {
            //lista proveedor actual
            List<Facturas> listaFacturas = facturaService.findFacturasByProveedorActual(usuarioLogeado);
            //Agregar la lista de productos al modelo
            model.addAttribute("facturas", listaFacturas);
        }

        //NUNCA DEBERIA DE SER VACIO YA QUE ENTRO PERO DEBERIAMOS REVISAR EL ASUNTO

        //model.addAttribute("clientes", clienteService.findAllClientes()); // Agrega la lista de clientes al modelo
        //List<Facturas> facturas = facturaService.findAllFacturas();
        //model.addAttribute("facturas", facturas);
        return "lista-facturas"; // nombre del archivo HTML de Thymeleaf
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
    @PostMapping("/buscarProducto")
    public String buscarProducto(@RequestParam("productoID") int productoId){
        Productos producto = productoService.findById(productoId);
        if(producto!=null){
            Detalle_Factura detalleFactura = new Detalle_Factura();
            detalleFactura.setCantidad(1);
            detalleFactura.setProducto(producto);
            detalleFactura.setPrecioUnitario(10.0);
            //listaItems.add(producto);
            listaDetalleFactura.add(detalleFactura);
            System.out.println("SI LO ENCONTRO\n\n\n\n\n\n\n");
        }else System.out.println("NO LO ENCONTRO\n\n\n\n\n\n\n");
        return "redirect:/facturas/new";
    }




}
