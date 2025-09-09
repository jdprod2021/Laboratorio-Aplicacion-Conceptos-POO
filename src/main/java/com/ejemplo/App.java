package com.ejemplo;

import com.ejemplo.Utils.InputUtils;
import com.ejemplo.ViewConsole.MainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ejemplo/ui/view/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Menú Principal - MVC JavaFX");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("====== MENÚ PRINCIPAL ======");
            System.out.println("1. Interfaz Grafica");
            System.out.println("2. Consola");
            System.out.println("0. Salir");

            int op = InputUtils.readInt("Seleccione una opción: ");
            switch (op) {
                case 1 -> launch();
                case 2 -> new MainMenu().start();
                case 0 -> {
                    System.out.println("Saliendo...");
                    return;
                }
                default -> System.out.println("Opción inválida.");
            }
            System.out.println(); // espacio al volver
        }
        
    }

}
