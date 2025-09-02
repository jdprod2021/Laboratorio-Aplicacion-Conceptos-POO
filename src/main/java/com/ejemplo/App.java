package com.ejemplo;

import com.ejemplo.Repositorios.Personas.EstudianteRepo;
import com.ejemplo.Repositorios.Personas.ProfesorRepo; // crea o usa tu versión de profesor
import com.ejemplo.Servicios.Impl.Personas.EstudianteImpl;
import com.ejemplo.Servicios.Impl.Personas.ProfesorImpl;
import com.ejemplo.Servicios.Personas.EstudianteServicio;
import com.ejemplo.Servicios.Personas.ProfesorServicio;
import com.ejemplo.ui.presenter.MainMenuPresenter;
import com.ejemplo.ui.view.MainMenuFrame;

public class App {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            var view = new MainMenuFrame();

            // Inyecta tus repos concretos
            var estudianteRepo = new EstudianteRepo();
            var profesorRepo   = new ProfesorRepo(); // o ProfesorRepoMemoria/DB según tengas

            EstudianteServicio estSrv = new EstudianteImpl(estudianteRepo);
            ProfesorServicio   profSrv = new ProfesorImpl(profesorRepo);

            new MainMenuPresenter(view, estSrv, profSrv);
            view.showUI();
        });
    }
}