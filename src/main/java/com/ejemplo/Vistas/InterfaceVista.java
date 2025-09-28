package com.ejemplo.Vistas;

import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;

/**
 * Interfaz común que deben implementar todas las vistas
 * Define el contrato básico para la interacción con el usuario
 */


public interface InterfaceVista {

    /**
     * Inicializa la vista (configuración inicial)
     */
    void inicializar();

    /**
     * Establece la fábrica de controladores que utilizará la vista
     * @param fabricaControladores La fábrica que proporciona acceso a todos los controladores
     */
    void setFabricaControladores(FabricaControladores fabricaControladores);

    /**
     * Muestra el menú principal de la aplicación
     */
    void mostrarMenuPrincipal();

    /**
     * Inicia el bucle principal de la aplicación
     */
    void ejecutar();

    /**
     * Muestra un mensaje informativo al usuario
     * @param mensaje El mensaje a mostrar
     */
    void mostrarMensaje(String mensaje);

    /**
     * Muestra un mensaje de error al usuario
     * @param error El mensaje de error a mostrar
     */
    void mostrarError(String error);

    /**
     * Cierra la vista y libera recursos
     */
    void cerrar();
}