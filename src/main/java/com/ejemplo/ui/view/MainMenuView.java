package com.ejemplo.ui.view;

public interface MainMenuView {
    public void showUI();
    public void onInscribirPersona(Runnable action);
    public void onInscribirCurso(Runnable action);
    public void onCursosInscritos(Runnable action);
    public void onCursosProfesores(Runnable action);
    public void onGestionarCursos(Runnable action);
    public void onGestionarProgramas(Runnable action);
    public void onGestionarFacultad(Runnable action);
    public void showMessage(String msg);
    public void showError(String msg, Throwable t);

}