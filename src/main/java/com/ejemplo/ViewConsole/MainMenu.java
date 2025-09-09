package com.ejemplo.ViewConsole;

import com.ejemplo.Utils.InputUtils;

public class MainMenu implements MenuView {

    @Override
    public void show() {
        System.out.println("====== MENÚ PRINCIPAL ======");
        System.out.println("1. Facultad");
        System.out.println("2. Programa");
        System.out.println("3. Estudiante");
        System.out.println("4. Profesor");
        System.out.println("0. Salir");
    }

    @Override
    public void handle() {
        while (true) {
            int op = InputUtils.readInt("Seleccione una opción: ");
            switch (op) {
                case 1 -> new FacultadView().start();
                case 2 -> new ProgramaView().start();
                case 3 -> new EstudianteView().start();
                case 4 -> new ProfesorView().start();
                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
            System.out.println(); // espacio al volver
            show(); // reimprime menú principal
        }
    }
}
