package com.ejemplo.ViewConsole;

public interface MenuView {
    void show(); // Pinta el menú de la vista
    void handle(); // Atiende la interacción del usuario
    default void start() {
        show();
        handle();
    }
}
