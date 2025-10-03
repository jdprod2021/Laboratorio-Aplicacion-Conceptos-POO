package com.ejemplo;

import com.ejemplo.Fabricas.FabricaExterna.FactoryExterna;
import com.ejemplo.Vistas.Interface.Vista;

public class Main {

    public static void main(String[] args) {

        FactoryExterna factoryExterna = FactoryExterna.crearFactoryExterna();

        Thread consoleThread = new Thread(() -> {
            Vista consoleApp = factoryExterna.crearVista("CONSOLA");
            consoleApp.inicializar();
        });

        Thread GUIThread = new Thread(() -> {
            Vista GUIApp = factoryExterna.crearVista("GUI");
            GUIApp.inicializar();
        });

        consoleThread.start();

        GUIThread.start();
    }
}