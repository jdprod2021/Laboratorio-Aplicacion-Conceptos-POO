package com.ejemplo.Vistas.Consola;

import com.ejemplo.Controladores.EstudianteControlador;
import com.ejemplo.DTOs.Solicitud.EstudianteSolicitudDTO;
import com.ejemplo.Utils.InputUtils;



public class VistaConsolaEstudiante {

    private EstudianteControlador controlador;

    public VistaConsolaEstudiante(EstudianteControlador controlador) {
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


    private void mostrarOpciones(){
         System.out.println("╔════════════════════════════════════════════╗");
            System.out.println("║            👨‍🏫 GESTIÓN DE ESTUDIANTE        ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. ➕ Crear Estudiante                        ║");
            System.out.println("║ 2. 📋 Listar Estudiantes                     ║");
            System.out.println("║ 3. ✏️  Actualizar Estudiante                   ║");
            System.out.println("║ 4. 🗑️  Eliminar Estudiante                     ║");
            System.out.println("║ 0. 🔙 Volver                                ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("👉 Seleccione una opción: ");
    }

    private boolean procesarOpcion(int opcion){
        switch (opcion) {
                case 1:
                    crearEstudiante();
                    break;
                case 2:
                    listarEstudiantes();
                    break;
                case 3: 
                    actualizarEstudiantes();
                    break;
                case 4:
                    eliminarEstudiantes();
                    break;
                case 0:
                    return true;
                default:
                    InputUtils.mostrarError("Opción inválida");
        }
                return false;
    }


        private void crearEstudiante(){

            System.out.println("=== Crear Nuevo Estudiante ===");
            System.out.print("Nombres: ");
            String nombres = InputUtils.readLine();
            
            System.out.print("Apellidos: ");
            String apellidos = InputUtils.readLine();

            System.out.print("Email: ");
            String email = InputUtils.readLine();

            System.out.print("Edad: ");
            long edad = InputUtils.readLong();

            System.out.print("Activo?: ");
            boolean activo = InputUtils.readBoolean();

            System.out.print("Promedio: ");
            double promedio = InputUtils.readDouble();

            System.out.print("Programa: ");
            long programaId = InputUtils.readLong();
          
            EstudianteSolicitudDTO EstudianteSolicitud = new EstudianteSolicitudDTO(nombres, apellidos, email, edad, activo, promedio, programaId);
            controlador.crearEstudiante(EstudianteSolicitud);
            InputUtils.mostrarMensaje("✅ Estudiante creado correctamente.");

         
        }



        private void listarEstudiantes(){
             
            System.out.println("\n=== Lista de Estudiantes ===");
            controlador.listarEstudiantes().forEach(curso -> 
            System.out.println("📚 " + curso.toString())
            );
        InputUtils.pausar();
        
        }



        private void actualizarEstudiantes(){

            System.out.println("\n=== Actualizar Estudiante ===");
            System.out.print("🔍 ID del curso a actualizar: ");
            long id = InputUtils.readLong();

            System.out.print("Nombres: ");
            String nombres = InputUtils.readLine();
            
            System.out.print("Apellidos: ");
            String apellidos = InputUtils.readLine();

            System.out.print("Email: ");
            String email = InputUtils.readLine();

            System.out.print("Edad: ");
            long edad = InputUtils.readLong();

            System.out.print("Activo?: ");
            boolean activo = InputUtils.readBoolean();

            System.out.print("Promedio: ");
            double promedio = InputUtils.readDouble();

            System.out.print("Programa: ");
            long programaId = InputUtils.readLong();
            
            EstudianteSolicitudDTO solicitud = new EstudianteSolicitudDTO(nombres, apellidos, email, edad, activo, promedio, programaId);
            controlador.actualizarEstudiante(id, solicitud);
            System.out.println("✅ Curso actualizado exitosamente.");


        }
        private void eliminarEstudiantes(){
                 
            System.out.println("\n=== Eliminar Estudiante ===");
            System.out.print("🔍 ID del curso a actualizar: ");
            long id = InputUtils.readLong();
            
            controlador.eliminarEstudiante(id);
            System.out.println("✅ Curso eliminado exitosamente.");
            InputUtils.pausar();
            
           
        }





          
}


