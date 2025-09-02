package com.ejemplo.ui.view;

import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.ProfesorServicio;

public interface MainMenuView {
    // Mostrar la UI
    void showUI();

    // Registro de callbacks (presenter -> vista)
    void onInscribirPersona(Runnable action);
    void onInscribirCurso(Runnable action);
    void onCursosInscritos(Runnable action);
    void onCursosProfesores(Runnable action);
    void onGestionarCursos(Runnable action);
    void onGestionarProgramas(Runnable action);
    void onGestionarFacultad(Runnable action);
    void setOnSalir(Runnable action);

    // Mensajería
    void showMessage(String msg);
    void showError(String msg, Throwable t);

    /* Compatibilidad con tu código existente (puedes seguir usándolo si lo llamas así)
    void setOnNuevoEstudiante(Runnable action);
*/
    // ⚠️ NUEVO: el presenter pide a la vista abrir el diálogo de inscripción
    void showInscribirPersonaDialog(EstudianteServicio estSrv, ProfesorServicio profSrv);
}
