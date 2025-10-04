package com.ejemplo.Vistas.Implementaciones;


import com.ejemplo.Fabricas.FabricaInterna.FabricaControladores;
import com.ejemplo.Utils.InputUtils;
import com.ejemplo.Vistas.Consola.*;
//import com.ejemplo.Vistas.Consola.VistaConsolaPrograma;
import com.ejemplo.Vistas.Interface.Vista;

public class VistaConsola implements Vista {

    private FabricaControladores fabricaControladores;
    private boolean aplicacionEjecutandose;

    public VistaConsola(FabricaControladores fabricaControladores) {
        this.fabricaControladores = fabricaControladores;
        this.aplicacionEjecutandose = true;
    }

    public void mostrarMenuPrincipal() {
        InputUtils.limpiarPantalla();
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

    public void cerrar() {
        InputUtils.mostrarMensaje("👋 Cerrando aplicación...");
        InputUtils.mostrarMensaje("¡Gracias por usar el Sistema Académico!");
        aplicacionEjecutandose = false;
        InputUtils.readLine();
    }

    @Override
    public void inicializar() {
        while (aplicacionEjecutandose) {
            try {
                mostrarMenuPrincipal();
                int opcion = InputUtils.readInt();
                procesarOpcionPrincipal(opcion);
            } catch (Exception e) {
                InputUtils.mostrarError("Error inesperado: " + e.getMessage());
                e.printStackTrace();
                InputUtils.pausar();
            }
        }
    }

    private void procesarOpcionPrincipal(int opcion) {
        switch (opcion) {
            case 1:
                new VistaConsolaProfesor(fabricaControladores.crearControladorProfesor()).mostrarMenu();
                break;
            case 2:
                new VistaConsolaFacultades(fabricaControladores.crearControladorFacultad()).mostrarMenu();
                break;
            case 3:
                new VistaConsolaPrograma(fabricaControladores.crearControladorPrograma()).mostrarMenu();
                break;
            case 4:
                new VistaConsolaCursos(fabricaControladores.crearControladorCurso()).mostrarMenu();
                break;
            case 5:
                new VistaConsolaEstudiante(fabricaControladores.crearControladorEstudiante()).mostrarMenu();    
                break;
            case 0:
                cerrar();
                break;
            default:
                if (opcion != -1) { // -1 ya mostró error
                    InputUtils.mostrarError("Opción no válida. Por favor seleccione una opción del 0 al 5.");
                    InputUtils.pausar();
                }
                break;
        }
    }

}
