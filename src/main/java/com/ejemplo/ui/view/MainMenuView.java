package com.ejemplo.ui.view;

public interface MainMenuView {
    void showUI();
    void onInscribirPersona(Runnable action);
    void onInscribirCurso(Runnable action);
    void onCursosInscritos(Runnable action);
    void onCursosProfesores(Runnable action);
    void onGestionarCursos(Runnable action);
    void onGestionarProgramas(Runnable action);
    void onGestionarFacultad(Runnable action);
    void showMessage(String msg);
    void showError(String msg, Throwable t);

}