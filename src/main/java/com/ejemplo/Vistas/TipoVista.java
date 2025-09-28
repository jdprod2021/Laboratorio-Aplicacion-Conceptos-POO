package com.ejemplo.Vistas;

/**
 * Enumeración que define los tipos de vista disponibles en la aplicación
 */
public enum TipoVista {
    /**
     * Vista para interfaz de consola/terminal
     */
    CONSOLA("Consola", "Interfaz de línea de comandos"),

    /**
     * Vista para interfaz gráfica de usuario (GUI)
     */
    GUI("GUI", "Interfaz gráfica de usuario");

    private final String nombre;
    private final String descripcion;

    TipoVista(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}