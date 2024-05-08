package com.example.proyecto_2_progra_4.logic.DTOEntities;

public class ProductosDTO {
    private int idProducto;
    private String nombre;
    private Object valor;  // Considera cambiar Object por un tipo más específico, como String o BigDecimal, dependiendo del tipo de dato de 'valor'.
    private int idProveedor;  // Solo el ID para mantener la simplicidad y evitar referencias circulares

    public ProductosDTO() {
        // Constructor vacío necesario para algunas operaciones de frameworks
    }

    public ProductosDTO(int idProducto, String nombre, Object valor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
        //this.idProveedor = idProveedor;
    }


    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    // Métodos toString(), equals() y hashCode() se pueden agregar según necesidad
}
