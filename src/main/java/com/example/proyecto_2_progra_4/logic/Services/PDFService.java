package com.example.proyecto_2_progra_4.logic.Services;

import com.example.proyecto_2_progra_4.logic.Entities.Facturas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PDFService {

    public ByteArrayOutputStream generarPdf(Facturas factura) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            // Definir fuente para el título
            BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD, new BaseColor(255, 0, 0)); // Rojo para el título
            Font normalFont = new Font(baseFont, 12, Font.NORMAL); // Normal para el resto del texto

            // Añadir título con fuente en rojo
            Paragraph title = new Paragraph("Sistema de Facturación", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Espacio después del título
            document.add(new Paragraph(" ")); // Agrega un espacio para separar el título de la tabla

            // Crear tabla con un solo elemento para actuar como cuadro
            PdfPTable table = new PdfPTable(1); // Tabla de una columna
            table.setWidthPercentage(100); // Hace que la tabla use todo el ancho de la página

            // Crear celda que contiene todos los elementos, con borde
            PdfPCell cell = new PdfPCell();
            cell.setBorder(Rectangle.BOX); // Establece el borde de la celda
            cell.setPadding(10); // Añade padding dentro de la celda

            // Añadir elementos al cuadro
            cell.addElement(new Paragraph("Factura ID: " + factura.getIdFactura(), normalFont));
            cell.addElement(new Paragraph("Proveedor ID: " + factura.getProveedoresByIdProveedor().getIdProveedor(), normalFont));
            cell.addElement(new Paragraph("Cliente ID: " + factura.getClientesByIdCliente().getIdCliente(), normalFont));
            cell.addElement(new Paragraph("Cliente: " + factura.getClientesByIdCliente().getNombre(), normalFont));
            cell.addElement(new Paragraph("Total: " + factura.getMonto(), normalFont));

            // Añadir celda a la tabla
            table.addCell(cell);

            // Añadir tabla al documento
            document.add(table);

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream;
    }
}
