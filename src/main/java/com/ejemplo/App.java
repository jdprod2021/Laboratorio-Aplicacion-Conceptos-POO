package com.ejemplo;

import com.ejemplo.Repositorios.DB;
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
        DB.initSchema();
        //new MainMenu().start();
        launch();
    }


}
