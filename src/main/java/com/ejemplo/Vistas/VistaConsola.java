package com.ejemplo.Vistas;

import java.util.Scanner;

import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;

/**
 * Implementación de vista para interfaz de consola
 * Maneja toda la interacción CRUD a través de la línea de comandos
 */
public class VistaConsola extends UtilsVistaConsola implements InterfaceVista {

    private FabricaControladores fabricaControladores;
    private boolean aplicacionEjecutandose;

    public VistaConsola() {
        super(new Scanner(System.in)); // Inicializamos UtilsVistaConsola con el scanner
        this.aplicacionEjecutandose = true;
    }

    @Override
    public void inicializar() {
        limpiarPantalla();
        mostrarBanner("SISTEMA DE GESTIÓN ACADÉMICA - INTERFAZ DE CONSOLA");
        System.out.println("✅ Vista de Consola inicializada correctamente.");
        System.out.println("📋 Presione ENTER para continuar...");
        scanner.nextLine();
    }

    @Override
    public void setFabricaControladores(FabricaControladores fabricaControladores) {
        this.fabricaControladores = fabricaControladores;
        System.out.println("🔗 Fábrica de controladores configurada correctamente.");
    }

    @Override
    public void mostrarMenuPrincipal() {
        limpiarPantalla();
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    🎓 SISTEMA ACADÉMICO 🎓                    ║");
        System.out.println("║                        MENÚ PRINCIPAL                         ║");
        System.out.println("╠═══════════════════════════════════════════════════════════════╣");
        System.out.println("║  1. 👨‍🏫 Gestión de Profesores                                ║");
        System.out.println("║  2. 🏛️  Gestión de Facultades                                ║");
        System.out.println("║  3. 📚 Gestión de Programas                                   ║");
        System.out.println("║  4. 📖 Gestión de Cursos                                      ║");
        System.out.println("║  5. 🎓 Gestión de Estudiantes                                 ║");
        System.out.println("║  0. 🚪 Salir                                                  ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        super.mostrarMensaje(mensaje);
    }

    @Override
    public void mostrarError(String error) {
        super.mostrarError(error);
    }

    @Override
    public void cerrar() {
        System.out.println("👋 Cerrando aplicación...");
        System.out.println("¡Gracias por usar el Sistema Académico!");
        aplicacionEjecutandose = false;
        scanner.close();
    }

    @Override
    public void ejecutar() {
        while (aplicacionEjecutandose) {
            try {
                mostrarMenuPrincipal();
                int opcion = leerOpcionMenu();
                procesarOpcionPrincipal(opcion);
            } catch (Exception e) {
                mostrarError("Error inesperado: " + e.getMessage());
                e.printStackTrace();
                pausar();
            }
        }
    }

    private void procesarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                new VistaConsolaProfesor(fabricaControladores, scanner).mostrarMenu();
                break;
            case 2:
                new VistaConsolaFacultades(fabricaControladores, scanner).mostrarMenu();
                break;
            case 3:
                new VistaConsolaPrograma(fabricaControladores, scanner).mostrarMenu();  
                break;
            case 4:
                new VistaConsolaCursos(fabricaControladores, scanner).mostrarMenu();
                break;
            case 5:
                new VistaConsolaEstudiante(fabricaControladores, scanner).mostrarMenu();    
                break;
            case 0:
                cerrar();
                break;
            default:
                if (opcion != -1) { // -1 ya mostró error
                    mostrarError("Opción no válida. Por favor seleccione una opción del 0 al 5.");
                    pausar();
                }
                break;
        }
    }

}
