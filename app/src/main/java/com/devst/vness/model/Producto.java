package com.devst.vness.model;

// Modelo que representa un Producto o Trámite del Registro Civil
public class Producto {
    private int id;
    private String nombre;
    private int stock; // Representa cupos disponibles o tiempo estimado en días
    private double precio; // Costo del trámite en pesos

    // Constructor completo
    public Producto(int id, String nombre, int stock, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
    }

    // Métodos Getter y Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}