package com.ejemplo.Vistas.Consola;


import com.ejemplo.Controladores.FacultadControlador;
import com.ejemplo.DTOs.Solicitud.FacultadSolicitudDTO;
import com.ejemplo.Utils.InputUtils;

public class VistaConsolaFacultades {

    private FacultadControlador controlador;
    

    public VistaConsolaFacultades(FacultadControlador controlador) {
        this.controlador = controlador;
    }
    
    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            mostrarOpciones();
            int opcion = InputUtils.readInt();
            volver = procesarOpcion(opcion);
        }
        }



    private void mostrarOpciones() {
        System.out.println("\n╔═══════════════════════════════╗");
        System.out.println("║     GESTIÓN DE FACULTADES     ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║ 1. 📝 Crear facultad          ║");
        System.out.println("║ 2. 📋 Listar facultades       ║");
        System.out.println("║ 3. 📋 Actualizar facultad     ║");
        System.out.println("║ 4. 🗑️  Eliminar facultad      ║");
        System.out.println("║ 0. ↩️  Volver al menú         ║");
        System.out.println("╚═══════════════════════════════╝");
        System.out.print("👉 Seleccione una opción: ");
    }

     private boolean procesarOpcion(int opcion){
        switch (opcion) {
                case 1:
                    crearFacultad();
                    break;
                case 2:
                    listarFacultades();
                    break;
                case 3: 
                    actualizarFacultad();
                    break;
                case 4:
                    eliminarFacultad();
                    break;
                case 0:
                    return true;
                default:
                    InputUtils.mostrarError("Opción inválida");
        }
                return false;
    }

    private void crearFacultad() {

        System.out.println("\n=== CREAR NUEVA FACULTAD ===");
        System.out.print("Nombre de la facultad: ");
        String nombre = InputUtils.readLine();


        System.out.print("ID del decano (persona): ");
        long decanoId = InputUtils.readLong();


        FacultadSolicitudDTO FacultadSolicitud = new FacultadSolicitudDTO(nombre, decanoId);
        controlador.crearFacultad(FacultadSolicitud);
        System.out.println("✅ Facultad creada con éxito.");
        InputUtils.pausar();
    }

    private void listarFacultades() {
        System.out.println("\n=== LISTA DE FACULTADES ===");
        controlador.listarFacultades()
                .forEach(facultad -> System.out.println(facultad.toString()));
        InputUtils.pausar();
    }

    private void actualizarFacultad() {
        System.out.println("\n=== ACTUALIZAR FACULTAD ===");
        System.out.print("Ingrese ID de la facultad a actualizar: ");
        long id = InputUtils.readLong();
        

        System.out.print("Nuevo nombre: ");
        String nombres = InputUtils.readLine();


        System.out.print("Nuevo ID del decano: ");
        long nuevoDecanoId = InputUtils.readLong();

        FacultadSolicitudDTO solicitud = new FacultadSolicitudDTO(nombres, nuevoDecanoId);
        controlador.actualizarFacultad(id, solicitud);
        System.out.println("✅ Facultad actualizada con éxito.");
    }

    private void eliminarFacultad() {
        System.out.println("\n=== ELIMINAR FACULTAD ===");
        System.out.print("Ingrese ID de la facultad a eliminar: ");
        long id = InputUtils.readLong();
        

        controlador.eliminarFacultad(id);
        System.out.println("🗑️ Facultad eliminada con éxito.");
        InputUtils.pausar();

    }

  
}